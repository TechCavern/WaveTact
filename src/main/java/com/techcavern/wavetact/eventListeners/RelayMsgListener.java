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
import org.pircbotx.Colors;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.NickChangeEvent;
import org.pircbotx.hooks.events.QuitEvent;

import static com.techcavern.wavetactdb.Tables.NETWORKPROPERTY;

/**
 * @author jztech101
 */
public class RelayMsgListener extends ListenerAdapter {

    @Override
    public void onNickChange(NickChangeEvent event) {
            IRCUtils.sendRelayMessage(event.getBot(), null, IRCUtils.colorizeNick(event.getBot(), event.getUser()) + IRCUtils.noPing(event.getOldNick()) + Colors.NORMAL + " is now known as " + IRCUtils.colorizeNick(event.getBot(), event.getUser()) + IRCUtils.noPing(event.getNewNick()), event.getUser());
    }

    @Override
    public void onQuit(QuitEvent event) {
            IRCUtils.sendRelayMessage(event.getBot(), null, IRCUtils.colorizeNick(event.getBot(), event.getUser()) + IRCUtils.noPing(event.getUser().getNick()) + Colors.NORMAL +" quits " + " (" + event.getReason() + ")", event.getUser());
    }
}







