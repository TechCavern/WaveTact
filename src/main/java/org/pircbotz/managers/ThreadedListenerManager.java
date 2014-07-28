package org.pircbotz.managers;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.Event;
import org.pircbotz.hooks.Listener;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

public class ThreadedListenerManager implements ListenerManager {

    private static final AtomicInteger MANAGER_COUNT = new AtomicInteger();
    private final int managerNumber;
    private final ExecutorService pool;
    private final Set<Listener> listeners = Collections.synchronizedSet(new HashSet<>());
    private final AtomicLong currentId = new AtomicLong();
    private final Multimap<PircBotZ, ManagedFutureTask> runningListeners = MultimapBuilder.hashKeys().linkedListValues().build();

    public ThreadedListenerManager() {
        managerNumber = MANAGER_COUNT.getAndIncrement();
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("listenerPool" + managerNumber + "-thread%d")
                .daemon(true)
                .build();
        ThreadPoolExecutor defaultPool = (ThreadPoolExecutor) Executors.newCachedThreadPool(factory);
        defaultPool.allowCoreThreadTimeOut(true);
        this.pool = defaultPool;
    }

    public ThreadedListenerManager(ExecutorService pool) {
        managerNumber = MANAGER_COUNT.getAndIncrement();
        this.pool = pool;
    }

    @Override
    public boolean addListener(Listener listener) {
        synchronized (listeners) {
            return listeners.add(listener);
        }
    }

    @Override
    public boolean removeListener(Listener listener) {
        synchronized (listeners) {
            return listeners.remove(listener);
        }
    }

    @Override
    public Set<Listener> getListeners() {
        synchronized (listeners) {
            return new HashSet<>(listeners);
        }
    }

    @Override
    public boolean listenerExists(Listener listener) {
        return getListeners().contains(listener);
    }

    @Override
    public void dispatchEvent(Event event) {
        synchronized (listeners) {
            for (Listener curListener : listeners) {
                submitEvent(pool, curListener, event);
            }
        }
    }

    void submitEvent(ExecutorService es, final Listener listener, final Event event) {
        es.execute(new ManagedFutureTask(listener, event, () -> {
            try {
                listener.onEvent(event);
            } catch (Exception e) {
            }
            return null;
        }));
    }

    @Override
    public void setCurrentId(long currentId) {
        this.currentId.set(currentId);
    }

    @Override
    public long getCurrentId() {
        return currentId.get();
    }

    @Override
    public long incrementCurrentId() {
        return currentId.getAndIncrement();
    }

    public ExecutorService shutdown() {
        pool.shutdown();
        return pool;
    }

    int getManagerNumber() {
        return managerNumber;
    }

    @Override
    public void shutdown(PircBotZ bot) {
        synchronized (runningListeners) {
            for (ManagedFutureTask curFuture : runningListeners.get(bot)) {
                try {
                    curFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException("Cannot shutdown listener " + curFuture.getListener() + " executing event " + curFuture.getEvent(), e);
                }
            }
        }
    }

    private class ManagedFutureTask extends FutureTask<Void> {

        private final Listener listener;
        private final Event event;

        public ManagedFutureTask(Listener listener, Event event, Callable<Void> callable) {
            super(callable);
            this.listener = listener;
            this.event = event;
            if (event.getBot() != null) {
                synchronized (runningListeners) {
                    runningListeners.put(event.getBot(), this);
                }
            }
        }

        @Override
        protected void done() {
            if (event.getBot() != null) {
                synchronized (runningListeners) {
                    runningListeners.remove(event.getBot(), this);
                }
            }
        }

        public Listener getListener() {
            return listener;
        }

        public Event getEvent() {
            return event;
        }
    }
}
