/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.KickEvent;

import java.util.concurrent.TimeUnit;


/**
 * @author jztech101
 */
public class KickListener extends ListenerAdapter {
    public void onKick(KickEvent event) throws Exception {
        Channel channel = event.getChannel();
        PircBotX network = event.getBot();
        User user = event.getUser();
        if (event.getRecipient().getNick().equals(event.getBot().getNick())) {
            int tries = 0;
            do {
                event.getBot().sendIRC().joinChannel(event.getChannel().getName());
                tries++;
                TimeUnit.SECONDS.sleep(5);
            } while (tries < 60 && !event.getBot().getUserBot().getChannels().contains(event.getChannel()));
            if (event.getBot().getUserBot().getChannels().contains(event.getChannel())) {
                if (channel != null && channel.getUserLevels(network.getUserBot()).contains(UserLevel.OP) && !channel.isOwner(user) && !channel.isSuperOp(user)) {
                    IRCUtils.sendKick(event.getBot().getUserBot(), user, network, event.getChannel(), "┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻");
                } else if (channel != null && channel.getUserLevels(network.getUserBot()).contains(UserLevel.SUPEROP) && !channel.isOwner(user) && !channel.isSuperOp(user)) {
                    IRCUtils.sendKick(event.getBot().getUserBot(), user, network, event.getChannel(), "┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻");
                } else if (channel != null && channel.getUserLevels(network.getUserBot()).contains(UserLevel.OWNER)) {
                    IRCUtils.sendKick(event.getBot().getUserBot(), user, network, event.getChannel(), "┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻");
                } else {
                    IRCUtils.sendAction(user, network, channel, "kicks " + user.getNick() + " (┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻)", "");

                }
            }
        }
    }
}
