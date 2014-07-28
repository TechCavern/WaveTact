package com.techcavern.wavetact.utils.eventListeners;

import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import com.techcavern.wavetact.utils.objects.PermChannel;
import org.pircbotz.hooks.ListenerAdapter;
import org.pircbotz.hooks.events.JoinEvent;
import org.pircbotz.hooks.events.QuitEvent;

/**
 * Created by jztech101 on 7/5/14.
 */
public class QuitListener extends ListenerAdapter {
    public void onQuit(QuitEvent event) {
        System.out.println(event.getUser().getNick() + "Has quit");
    }
}


