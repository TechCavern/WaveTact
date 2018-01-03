/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetactdb.tables.Channelproperty;
import org.jooq.Record;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;

import static com.techcavern.wavetactdb.Tables.CHANNELUSERPROPERTY;


/**
 * @author jztech101
 */
public class JoinListener extends ListenerAdapter {

    @Override
    public void onJoin(JoinEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                if (PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel()) > -3) {
                    IRCUtils.addVoice(event.getBot(), event.getChannel(), event.getUser());
                }
                IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), IRCUtils.noPing(event.getUser().getNick()) + " (" + event.getUserHostmask().getLogin() + "@" + event.getUserHostmask().getHostname() + ") joined " + event.getChannel().getName());
                Record autoop = DatabaseUtils.getChannelUserProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), PermUtils.authUser(event.getBot(), event.getUser().getNick()), "autoop");
                if (!event.getBot().getNick().equalsIgnoreCase(event.getUser().getNick()) && autoop != null && autoop.getValue(CHANNELUSERPROPERTY.VALUE).equalsIgnoreCase("true")) {
                    int permlevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel());
                    if (permlevel >= 15) {
                        if (event.getBot().getServerInfo().getPrefixes().contains("q")) {
                            IRCUtils.setMode(event.getChannel(), event.getBot(), "+qo", event.getUser().getNick() + " " + event.getUser().getNick());
                        } else if (event.getBot().getServerInfo().getPrefixes().contains("a")) {
                            IRCUtils.setMode(event.getChannel(), event.getBot(), "+ao", event.getUser().getNick() + " " + event.getUser().getNick());
                        } else {
                            IRCUtils.setMode(event.getChannel(), event.getBot(), "+o", event.getUser().getNick());
                        }
                    } else if (permlevel >= 13) {
                        if (event.getBot().getServerInfo().getPrefixes().contains("a")) {
                            IRCUtils.setMode(event.getChannel(), event.getBot(), "+ao", event.getUser().getNick() + " " + event.getUser().getNick());
                        } else {
                            IRCUtils.setMode(event.getChannel(), event.getBot(), "+o", event.getUser().getNick());
                        }
                    } else if (permlevel >= 10) {
                        IRCUtils.setMode(event.getChannel(), event.getBot(), "+o", event.getUser().getNick());

                    } else if (permlevel >= 7) {
                        if (event.getBot().getServerInfo().getPrefixes().contains("h")) {
                            IRCUtils.setMode(event.getChannel(), event.getBot(), "+h", event.getUser().getNick());
                        } else {
                            IRCUtils.setMode(event.getChannel(), event.getBot(), "+v", event.getUser().getNick());
                        }
                    } else if (permlevel >= 5) {
                        IRCUtils.setMode(event.getChannel(), event.getBot(), "+v", event.getUser().getNick());
                    } else if (permlevel <= -4) {
                        IRCUtils.setMode(event.getChannel(), event.getBot(), "+b", IRCUtils.getHostmask(event.getBot(), event.getUser().getNick(), true));
                        IRCUtils.sendKick(event.getUser(), IRCUtils.getUserByNick(event.getBot(), event.getUser().getNick()), event.getBot(), event.getChannel(), "Banned - Your behavior is not conducive to the desired environment");
                    }

                }
                Record entrymsg = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), "entrymsg");
                if (entrymsg != null && !entrymsg.getValue(Channelproperty.CHANNELPROPERTY.VALUE).isEmpty()) {
                    IRCUtils.sendNotice(event.getUser(), event.getBot(), null, "["+event.getChannel().getName() +"] " +entrymsg.getValue(Channelproperty.CHANNELPROPERTY.VALUE), null);

                }
            }
        }
        Registry.threadPool.execute(new process());
    }

}
/**
**/