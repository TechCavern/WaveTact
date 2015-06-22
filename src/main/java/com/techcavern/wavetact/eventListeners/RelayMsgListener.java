/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import org.jooq.Record;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

import static com.techcavern.wavetactdb.Tables.NETWORKPROPERTY;

/**
 * @author jztech101
 */
public class RelayMsgListener extends ListenerAdapter {
    @Override
    public void onMessage(MessageEvent event) throws Exception {
        if(PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel()) > -2)
        IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), "<" + GeneralUtils.replaceVowelsWithAccents(event.getUser().getNick()) + "> " + event.getMessage());
    }

    @Override
    public void onAction(ActionEvent event) {
        if(PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel()) > -2)
            IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), "* " + GeneralUtils.replaceVowelsWithAccents(event.getUser().getNick()) + " " + event.getMessage());
    }

    @Override
    public void onNickChange(NickChangeEvent event) {
        Record rec = DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "relaychan");
        String chanrelay = null;
        if(rec != null)
            chanrelay = rec.getValue(NETWORKPROPERTY.VALUE);
        if (chanrelay != null && event.getUser().getChannels().contains(IRCUtils.getChannelbyName(event.getBot(), chanrelay)))
            IRCUtils.sendRelayMessage(event.getBot(), IRCUtils.getChannelbyName(event.getBot(), chanrelay), "-" + GeneralUtils.replaceVowelsWithAccents(event.getOldNick()) + " is now known as " + GeneralUtils.replaceVowelsWithAccents(event.getNewNick()));
    }

    @Override
    public void onKick(KickEvent event) {
        IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(),GeneralUtils.replaceVowelsWithAccents(event.getUser().getNick()) + " kicks " + GeneralUtils.replaceVowelsWithAccents(event.getRecipient().getNick()) + " (" + event.getReason() + ")");
    }

    @Override
    public void onPart(PartEvent event) {
        IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), GeneralUtils.replaceVowelsWithAccents(event.getUser().getNick()) + " left " + event.getChannel().getName() + " (" + event.getReason() + ")");
    }

    @Override
    public void onQuit(QuitEvent event) {
        Record rec = DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "relaychan");
        String chanrelay = null;
        if(rec != null)
        chanrelay = rec.getValue(NETWORKPROPERTY.VALUE);
        if (chanrelay != null && event.getUser().getChannels().contains(IRCUtils.getChannelbyName(event.getBot(), chanrelay)))
            IRCUtils.sendRelayMessage(event.getBot(), IRCUtils.getChannelbyName(event.getBot(), chanrelay), GeneralUtils.replaceVowelsWithAccents(event.getUser().getNick()) + " quits " + " (" + event.getReason() + ")");
    }

    @Override
    public void onJoin(JoinEvent event) {
        IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), GeneralUtils.replaceVowelsWithAccents(event.getUser().getNick()) + " joined " + event.getChannel().getName());
    }
}







