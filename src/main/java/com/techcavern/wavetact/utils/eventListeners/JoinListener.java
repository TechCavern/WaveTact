package com.techcavern.wavetact.utils.eventListeners;

import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import com.techcavern.wavetact.utils.objects.PermChannel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;

/**
 * Created by jztech101 on 7/5/14.
 */
public class JoinListener extends ListenerAdapter<PircBotX> {
    public void onJoin(JoinEvent<PircBotX> event) throws Exception {
        PermChannel PLChannel = PermChannelUtils.getPermLevelChannel(event.getBot().getServerInfo().getNetwork(), PermUtils.getAuthedAccount(event.getBot(), event.getUser().getNick(), false), event.getChannel().getName());
        if (PLChannel != null) {
            if (PLChannel.getAuto()) {
                int i = PLChannel.getPermLevel();
                if (i >= 15) {
                    if (event.getBot().getServerInfo().getPrefixes().contains("q")) {
                        event.getChannel().send().owner(event.getUser());
                    } else {
                        event.getChannel().send().op(event.getUser());
                    }
                } else if (i >= 13) {
                    if (event.getBot().getServerInfo().getPrefixes().contains("a")) {
                        event.getChannel().send().superOp(event.getUser());
                    } else {
                        event.getChannel().send().op(event.getUser());
                    }
                } else if (i >= 10) {
                    event.getChannel().send().op(event.getUser());
                } else if (i >= 7) {
                    if (event.getBot().getServerInfo().getPrefixes().contains("h")) {
                        event.getChannel().send().halfOp(event.getUser());
                    } else {
                        event.getChannel().send().voice(event.getUser());
                    }
                } else if (i >= 5) {
                    event.getChannel().send().voice(event.getUser());
                } else if (i == -1) {
                    IRCUtils.setMode(event.getChannel(), event.getBot(), "+b ", IRCUtils.getHostmask(event.getBot(), event.getUser().getNick(), true));
                }
            }
        }
    }
}


