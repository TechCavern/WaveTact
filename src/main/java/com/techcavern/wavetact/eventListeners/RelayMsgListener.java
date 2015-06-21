/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

import static com.techcavern.wavetactdb.Tables.NETWORKPROPERTY;

/**
 * @author jztech101
 */
public class RelayMsgListener extends ListenerAdapter {
    @Override
    public void onMessage(MessageEvent event) throws Exception {
        IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), "<-" + event.getUser().getNick() + "> " + event.getMessage());
    }

    @Override
    public void onAction(ActionEvent event) {
        IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), "* " + "-" + event.getUser().getNick() + " " + event.getMessage());
    }

    @Override
    public void onNickChange(NickChangeEvent event) {
        String chanrelay = DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "relaychan").getValue(NETWORKPROPERTY.VALUE);
        if (event.getUser().getChannels().contains(IRCUtils.getChannelbyName(event.getBot(), chanrelay)))
            IRCUtils.sendRelayMessage(event.getBot(), IRCUtils.getChannelbyName(event.getBot(), chanrelay), "-" + event.getOldNick() + " is now known as " + event.getNewNick());
    }

    @Override
    public void onKick(KickEvent event) {
        IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), "-" + event.getUser().getNick() + " kicks " + event.getRecipient().getNick() + " (" + event.getReason() + ")");
    }

    @Override
    public void onPart(PartEvent event) {
        IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), "-" + event.getUser().getNick() + " parts " + " (" + event.getReason() + ")");
    }

    @Override
    public void onQuit(QuitEvent event) {
        String chanrelay = DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "relaychan").getValue(NETWORKPROPERTY.VALUE);
        if (event.getUser().getChannels().contains(IRCUtils.getChannelbyName(event.getBot(), chanrelay)))
            IRCUtils.sendRelayMessage(event.getBot(), IRCUtils.getChannelbyName(event.getBot(), chanrelay), "-" + event.getUser().getNick() + " quits " + " (" + event.getReason() + ")");
    }

    @Override
    public void onJoin(JoinEvent event) {
        IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), "-" + event.getUser().getNick() + " joins");
    }
}







