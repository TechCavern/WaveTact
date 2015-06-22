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
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import java.util.concurrent.TimeUnit;

import static com.techcavern.wavetactdb.Tables.CHANNELPROPERTY;
import static com.techcavern.wavetactdb.Tables.TELLMESSAGES;

/**
 * @author jztech101
 */
public class TellMsgListener extends ListenerAdapter {
    @Override
    public void onMessage(MessageEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                TellMessageTrigger(event.getBot(), event.getUser(), event.getChannel(), IRCUtils.getPrefix(event.getBot(), event.getChannelSource()));
            }
            }
        Registry.threadPool.execute(new process());
    }
    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                TellMessageTrigger(event.getBot(), event.getUser(), null, null);
            }
        }
        Registry.threadPool.execute(new process());
    }
    public static void TellMessageTrigger(PircBotX bot, User user, Channel channel, String prefix){
        String network = IRCUtils.getNetworkNameByNetwork(bot);
        Result<Record> TellMessages = DatabaseUtils.getTellMessage(network, PermUtils.authUser(bot, user.getNick()));
        if(TellMessages != null && TellMessages.isNotEmpty()){
            if(channel != null){
                IRCUtils.sendMessage(user, bot,channel, "[" + user.getNick() + "] - Someone send you a latent message while you were away! Please Check your PMs", prefix);
            }
            for(Record rec:TellMessages){
                IRCUtils.sendMessage(user, bot,null, "[" + rec.getValue(TELLMESSAGES.SENDER) + "] - " + rec.getValue(TELLMESSAGES.MESSAGE), null);
            }
            DatabaseUtils.removeTellMessage(network, PermUtils.authUser(bot, user.getNick()));
        }
    }
}






