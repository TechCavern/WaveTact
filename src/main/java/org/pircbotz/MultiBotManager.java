package org.pircbotz;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service.State;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.pircbotz.exception.IrcException;
import org.apache.commons.lang3.Validate;

public class MultiBotManager {

    private static final AtomicInteger MANAGER_COUNT = new AtomicInteger();
    private final int managerNumber;
    private final LinkedHashMap<PircBotZ, Future<Void>> runningBots = new LinkedHashMap<>();
    private final Map<PircBotZ, Integer> runningBotsNumbers = new ConcurrentHashMap<>();
    private final Object runningBotsLock = new Object[0];
    private final ListeningExecutorService botPool;
    private final List<PircBotZ> startQueue = new ArrayList<>();
    private State state = State.NEW;
    private final Object stateLock = new Object();

    public MultiBotManager() {
        managerNumber = MANAGER_COUNT.getAndIncrement();
        ThreadPoolExecutor defaultPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        defaultPool.allowCoreThreadTimeOut(true);
        this.botPool = MoreExecutors.listeningDecorator(defaultPool);
    }

    public MultiBotManager(ExecutorService botPool) {
        Validate.notNull(botPool, "Bot pool cannot be null");
        this.botPool = MoreExecutors.listeningDecorator(botPool);
        this.managerNumber = MANAGER_COUNT.getAndIncrement();
    }

    public void addBot(Configuration<PircBotZ> config) {
        Validate.notNull(config, "Configuration cannot be null");
        if (state != State.NEW && state != State.RUNNING) {
            throw new RuntimeException("MultiBotManager is not running. State: " + state);
        }
        addBot(new PircBotZ(config));
    }

    public void addBot(PircBotZ bot) {
        Validate.notNull(bot, "Bot cannot be null");
        Validate.isTrue(!bot.isConnected(), "Bot must not already be connected");
        if (state == State.NEW) {
            startQueue.add(bot);
        } else if (state == State.RUNNING) {
            startBot(bot);
        } else {
            throw new RuntimeException("MultiBotManager is not running. State: " + state);
        }
    }

    public void start() {
        synchronized (stateLock) {
            if (state != State.NEW) {
                throw new RuntimeException("MultiBotManager has already been started. State: " + state);
            }
            state = State.STARTING;
        }
        for (PircBotZ bot : startQueue) {
            startBot(bot);
        }
        startQueue.clear();
        synchronized (stateLock) {
            state = State.RUNNING;
        }
    }

    Future<Void> startBot(final PircBotZ bot) {
        Validate.notNull(bot, "Bot cannot be null");
        ListenableFuture<Void> future = botPool.submit(new BotRunner(bot));
        synchronized (runningBotsLock) {
            runningBots.put(bot, future);
            runningBotsNumbers.put(bot, bot.getBotId());
        }
        Futures.addCallback(future, new BotFutureCallback(bot));
        return future;
    }

    public void stop() {
        synchronized (stateLock) {
            if (state != State.RUNNING) {
                throw new RuntimeException("MultiBotManager cannot be stopped again or before starting. State: " + state);
            }
            state = State.STOPPING;
        }
        for (PircBotZ bot : runningBots.keySet()) {
            if (bot.isConnected()) {
                bot.sendIRC().quitServer();
            }
        }
        botPool.shutdown();
    }

    public void stopAndWait() throws InterruptedException {
        stop();
        do {
            synchronized (runningBotsLock) {
            }
        } while (!botPool.awaitTermination(5, TimeUnit.SECONDS));
    }

    public Set<PircBotZ> getBots() {
        return new HashSet<>(runningBots.keySet());
    }

    public PircBotZ getBotById(int id) {
        for (Entry<PircBotZ, Integer> entry : runningBotsNumbers.entrySet()) {
            if (entry.getValue() == id) {
                return entry.getKey();
            }
        }
        return null;
    }

    private class BotRunner implements Callable<Void> {

        final PircBotZ bot;

        public BotRunner(PircBotZ bot) {
            this.bot = bot;
        }

        @Override
        public Void call() throws IOException, IrcException {
            Thread.currentThread().setName("botPool" + managerNumber + "-bot" + bot.getBotId());
            bot.connect();
            return null;
        }
    }

    class BotFutureCallback implements FutureCallback<Void> {

        final PircBotZ bot;

        public BotFutureCallback(PircBotZ bot) {
            this.bot = bot;
        }

        @Override
        public void onSuccess(Void result) {
            remove();
        }

        @Override
        public void onFailure(Throwable t) {
            remove();
        }

        void remove() {
            synchronized (runningBotsLock) {
                runningBots.remove(bot);
                runningBotsNumbers.remove(bot);
                if (runningBots.isEmpty() && state == State.STOPPING) {
                    synchronized (stateLock) {
                        if (state == State.STOPPING) {
                            state = State.TERMINATED;
                        }
                    }
                }
            }
        }
    }
}
