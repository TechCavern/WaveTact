/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.techcavern.wavetact.eventListeners;

import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetactdb.tables.Channelproperty;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import org.jooq.Record;
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
        IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), IRCUtils.noPing(event.getRecipient().getNick()) + " (" + event.getRecipientHostmask().getLogin() + "@" + event.getRecipientHostmask().getHostname() +") was kicked by " + IRCUtils.noPing(event.getUser().getNick()) + " (" + event.getUserHostmask().getLogin() + "@" + event.getUserHostmask().getHostname() +") (" + event.getReason() + ")");
        Record rec = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), "kickrejoin");
        if (!(rec != null && rec.getValue(Channelproperty.CHANNELPROPERTY.VALUE).equalsIgnoreCase("false"))) {
            Channel channel = event.getChannel();
            PircBotX network = event.getBot();
            User user = event.getUser();
            if (event.getRecipient().getNick().equals(network.getNick())) {
                int tries = 0;
                event.getBot().sendIRC().joinChannel(channel.getName());
                while (tries < 10 && !network.getUserBot().getChannels().contains(channel)) {
                    event.getBot().sendIRC().joinChannel(channel.getName());
                    tries++;
                    TimeUnit.SECONDS.sleep(5);
                }
                Record rec2 = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), "funmsg");
                if (rec2 == null)
                    return;
                if (rec2.getValue(Channelproperty.CHANNELPROPERTY.VALUE).equalsIgnoreCase("true")) {
                    if (network.getUserBot().getChannels().contains(channel) && !user.getNick().equals(network.getNick())) {
                        if (IRCUtils.checkIfCanKick(channel, network, user)) {
                            IRCUtils.sendKick(network.getUserBot(), user, network, event.getChannel(), "┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻");
                        } else {
                            IRCUtils.sendAction(user, network, channel, "kicks " + IRCUtils.noPing(user.getNick()) + " (┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻)", "");
                        }
                    }
                }
            }
        }
    }
}
