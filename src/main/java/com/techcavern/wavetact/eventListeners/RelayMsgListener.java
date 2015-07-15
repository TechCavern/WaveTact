/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import org.jooq.Record;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.NickChangeEvent;
import org.pircbotx.hooks.events.QuitEvent;

import static com.techcavern.wavetactdb.Tables.NETWORKPROPERTY;

/**
 * @author jztech101
 */
public class RelayMsgListener extends ListenerAdapter {

    @Override
    public void onAction(ActionEvent event) {
        if (PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel()) > -3 && IRCUtils.getPrefix(event.getBot(), event.getChannelSource()).isEmpty())
            IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), "* " + IRCUtils.noPing(event.getUser().getNick()) + " " + event.getMessage());
    }

    @Override
    public void onNickChange(NickChangeEvent event) {
        Record rec = DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "relaychan");
        String chanrelay = null;
        if (rec != null)
            chanrelay = rec.getValue(NETWORKPROPERTY.VALUE);
        if (chanrelay != null && event.getUser().getChannels().contains(IRCUtils.getChannelbyName(event.getBot(), chanrelay)))
            IRCUtils.sendRelayMessage(event.getBot(), IRCUtils.getChannelbyName(event.getBot(), chanrelay), IRCUtils.noPing(event.getOldNick()) + " is now known as " + IRCUtils.noPing(event.getNewNick()));
    }

    @Override
    public void onQuit(QuitEvent event) {
        Record rec = DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "relaychan");
        String chanrelay = null;
        if (rec != null)
            chanrelay = rec.getValue(NETWORKPROPERTY.VALUE);
        if (chanrelay != null && event.getUser().getChannels().contains(IRCUtils.getChannelbyName(event.getBot(), chanrelay)))
            IRCUtils.sendRelayMessage(event.getBot(), IRCUtils.getChannelbyName(event.getBot(), chanrelay), IRCUtils.noPing(event.getUser().getNick()) + " quits " + " (" + event.getReason() + ")");
    }

    @Override
    public void onJoin(JoinEvent event) {
        IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), IRCUtils.noPing(event.getUser().getNick()) + " joined " + event.getChannel().getName());
    }
}







