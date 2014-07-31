package com.techcavern.wavetact.utils.eventListeners;

import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import com.techcavern.wavetact.utils.objects.PermChannel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.PartEvent;

/**
 * Created by jztech101 on 7/5/14.
 */
public class PartListener extends ListenerAdapter<PircBotX> {
    public void onPart(PartEvent<PircBotX> event) throws Exception {
       event.getUser().send().notice("hi");
    }
}


