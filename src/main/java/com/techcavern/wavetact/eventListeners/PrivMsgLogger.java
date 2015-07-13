/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.jooq.Record;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import static com.techcavern.wavetactdb.Tables.NETWORKPROPERTY;

/**
 * @author jztech101
 */
public class PrivMsgLogger extends ListenerAdapter {

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        Record pmlog = DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "pmlog");
        if (pmlog != null && IRCUtils.getChannelbyName(event.getBot(), pmlog.getValue(NETWORKPROPERTY.VALUE)) != null)
            IRCUtils.sendMessage(null, event.getBot(), IRCUtils.getChannelbyName(event.getBot(), pmlog.getValue(NETWORKPROPERTY.VALUE)), "[" + GeneralUtils.replaceVowelsWithAccents(event.getUser().getNick()) + "] " + event.getMessage(), "");
    }
}





