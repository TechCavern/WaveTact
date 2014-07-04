package com.techcavern.wavetact.events;


import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.DisconnectEvent;

public class DisconnectListener extends ListenerAdapter<PircBotX> {

    public void onDisconnect(DisconnectEvent<PircBotX> event) throws Exception {
        event.getBot().stopBotReconnect();
//        event.getBot().sendIRC().quitServer();
        Thread.sleep(20000);
        event.getBot().startBot();
    }


}
