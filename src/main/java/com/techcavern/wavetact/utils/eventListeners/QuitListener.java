package com.techcavern.wavetact.utils.eventListeners;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.PermUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.QuitEvent;

/**
 * Created by jztech101 on 7/5/14.
 */
public class QuitListener extends ListenerAdapter<PircBotX> {
    public void onQuit(QuitEvent<PircBotX> event) throws Exception {
        GeneralRegistry.AuthedUsers.remove(PermUtils.getAuthUser(event.getBot(), event.getUser().getNick()));
    }
}


