package com.techcavern.wavetact.utils.eventListeners;

import org.pircbotz.exception.IrcException;
import org.pircbotz.hooks.ListenerAdapter;
import org.pircbotz.hooks.events.DisconnectEvent;
import org.pircbotz.hooks.events.QuitEvent;

import java.io.IOException;

/**
 * Created by jztech101 on 7/5/14.
 */
public class DisconnectListener extends ListenerAdapter {
    public void onDisconnect(DisconnectEvent event) {
        event.getBot().shutdown(true);
        try {
            event.getBot().startBot();
        }catch(IrcException | IOException e){
            e.printStackTrace();
        }
    }
}


