/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.jooq.Record;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Colors;
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import static com.techcavern.wavetactdb.Tables.CHANNELPROPERTY;
import static com.techcavern.wavetactdb.Tables.CHANNELUSERPROPERTY;

/**
 * @author jztech101
 */
public class FunMsgListener extends ListenerAdapter {
    @Override
    public void onMessage(MessageEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                Record relaybotsplit = DatabaseUtils.getChannelUserProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), PermUtils.authUser(event.getBot(), event.getUser().getNick()), "relaybotsplit");
                if (relaybotsplit == null) {
                    if (event.getMessage().contains("http://stop-irc-bullying.eu/stop")) {
                        if (IRCUtils.checkIfCanKick(event.getChannel(), event.getBot(), event.getUser())) {
                            event.getChannel().send().kick(event.getUser(), "┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻");
                        } else {
                            IRCUtils.sendAction(event.getUser(), event.getBot(), event.getChannel(), "kicks " + event.getUser().getNick() + " (┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻)", "");
                        }
                    }
                }
                Record autourl = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), "autourl");
                if (autourl != null && autourl.getValue(CHANNELPROPERTY.VALUE).equalsIgnoreCase("true")) {
                    String[] message = StringUtils.split(Colors.removeFormattingAndColors(event.getMessage()), " ");
                    for (String arg : message) {
                        try {
                            if (!arg.startsWith("http://") || !arg.startsWith("https://")) {
                                arg = "http://" + arg;
                            }
                            if(Registry.urlvalidator.isValid(arg)) {
                                Document doc = Jsoup.connect(arg).userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17").get();
                                String title = doc.title();
                                if (!title.isEmpty()) {
                                    IRCUtils.sendMessage(event.getBot(), event.getChannel(), "[" + event.getUser().getNick() + "] - " + title, "");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        Registry.threadPool.execute(new process());
    }
}







