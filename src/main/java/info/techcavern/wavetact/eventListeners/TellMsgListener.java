/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.techcavern.wavetact.eventListeners;

import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.PermUtils;
import info.techcavern.wavetact.utils.Registry;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.PermUtils;
import info.techcavern.wavetact.utils.Registry;
import org.jooq.Record;
import org.jooq.Result;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import static info.techcavern.wavetactdb.Tables.TELLMESSAGES;

/**
 * @author jztech101
 */
public class TellMsgListener extends ListenerAdapter {
    public static void TellMessageTrigger(PircBotX network, User user, Channel channel, String prefix) {
        String networkName = IRCUtils.getNetworkNameByNetwork(network);
        Result<Record> TellMessages = DatabaseUtils.getTellMessage(networkName, PermUtils.authUser(network, user.getNick()));
        if (TellMessages != null && TellMessages.isNotEmpty()) {
            if (channel != null) {
                IRCUtils.sendMessage(user, network, channel, "[" + user.getNick() + "] - Someone sent you a latent message while you were away! Please Check your PMs", prefix);
            }
            for (Record rec : TellMessages) {
                IRCUtils.sendMessage(user, network, null, "[" + rec.getValue(TELLMESSAGES.SENDER) + "] - " + rec.getValue(TELLMESSAGES.MESSAGE), null);
            }
            DatabaseUtils.removeTellMessage(networkName, PermUtils.authUser(network, user.getNick()));
        }
    }

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
    public void onAction(ActionEvent event) throws Exception {
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
}







