/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import static com.techcavern.wavetactdb.Tables.NETWORKPROPERTY;

/**
 * @author jztech101
 */
public class ActionListener extends ListenerAdapter {

    @Override
    public void onAction(ActionEvent event) {
        if (PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel()) > -4 && IRCUtils.getPrefix(event.getBot(), event.getChannelSource()).isEmpty())
            IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), "* " + IRCUtils.noPing(event.getUser().getNick()) + " " + event.getMessage());
        if (PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel()) > -3){
            IRCUtils.addVoice(event.getBot(), event.getChannel(), event.getUser());
        }
    }


    }





