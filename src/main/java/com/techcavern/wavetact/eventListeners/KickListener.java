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
        if (event.getRecipient().getNick().equals(network.getNick())) {
            int tries = 0;
            do {
                event.getBot().sendIRC().joinChannel(channel.getName());
                tries++;
                TimeUnit.SECONDS.sleep(5);
            } while (tries < 60 && !network.getUserBot().getChannels().contains(channel));
            if (network.getUserBot().getChannels().contains(channel) && !user.getNick().equals(network.getNick())) {
                if (IRCUtils.checkIfCanKick(channel, network, user)) {
                    IRCUtils.sendKick(network.getUserBot(), user, network, event.getChannel(), "┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻");
                } else {
                    IRCUtils.sendAction(user, network, channel, "kicks " + user.getNick() + " (┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻)", "");

                }
            }
        }
    }
}
