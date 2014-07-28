package org.pircbotz.hooks;

public interface Listener {

    public void onEvent(Event event) throws Exception;
}
