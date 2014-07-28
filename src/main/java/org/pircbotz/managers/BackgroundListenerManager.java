package org.pircbotz.managers;

import com.google.common.collect.ImmutableSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.pircbotz.hooks.Event;
import org.pircbotz.hooks.Listener;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

public class BackgroundListenerManager extends ThreadedListenerManager {

    private final Map<Listener, ExecutorService> backgroundListeners = new HashMap<>();
    private final AtomicInteger backgroundCount = new AtomicInteger();

    public boolean addListener(Listener listener, boolean isBackground) {
        if (!isBackground) {
            return super.addListener(listener);
        }
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("backgroundPool" + getManagerNumber() + "-backgroundThread" + backgroundCount.getAndIncrement() + "-%d")
                .daemon(true)
                .build();
        backgroundListeners.put(listener, Executors.newSingleThreadExecutor(factory));
        return true;
    }

    @Override
    public void dispatchEvent(Event event) {
        super.dispatchEvent(event);
        for (Map.Entry<Listener, ExecutorService> curEntry : backgroundListeners.entrySet()) {
            submitEvent(curEntry.getValue(), curEntry.getKey(), event);
        }
    }

    @Override
    public ImmutableSet<Listener> getListeners() {
        HashSet<Listener> set = new HashSet<>();
        set.addAll(getListeners());
        set.addAll(backgroundListeners.keySet());
        return new ImmutableSet.Builder<Listener>().addAll(set).build();
    }

    @Override
    public boolean removeListener(Listener listener) {
        if (backgroundListeners.containsKey(listener)) {
            return backgroundListeners.remove(listener) != null;
        } else {
            return super.removeListener(listener);
        }
    }
}
