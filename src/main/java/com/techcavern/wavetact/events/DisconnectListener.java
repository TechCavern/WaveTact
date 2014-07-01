package com.techcavern.wavetact.events;

/**
 * Created by jztech101 on 7/1/14.
 */

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.DisconnectEvent;

public class DisconnectListener extends ListenerAdapter<PircBotX> {
    public void onDisconnect(DisconnectEvent<PircBotX> event) throws Exception{
        event.getBot().startBot();
    }


}
