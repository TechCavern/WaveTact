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
import info.techcavern.wavetact.utils.IRCUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.NickChangeEvent;
import org.pircbotx.hooks.events.QuitEvent;
import org.pircbotx.hooks.events.TopicEvent;

import java.util.HashSet;
import java.util.Set;

import static info.techcavern.wavetactdb.Tables.NETWORKPROPERTY;
import static info.techcavern.wavetactdb.Tables.RELAYS;

/**
 * @author jztech101
 */
public class RelayMsgListener extends ListenerAdapter {

    @Override
    public void onNickChange(NickChangeEvent event) {
        IRCUtils.sendRelayMessage(event.getBot(), null, IRCUtils.noPing(event.getOldNick()) + " is now known as " + IRCUtils.noPing(event.getNewNick()), event.getUser());
        IRCUtils.updateVoice(event.getBot(), event.getOldNick(), event.getUser());
    }

    @Override
    public void onQuit(QuitEvent event) {
        IRCUtils.removeVoice(event.getBot(), event.getUser());
        IRCUtils.sendRelayMessage(event.getBot(), null, IRCUtils.noPing(event.getUser().getNick()) + " (" + event.getUserHostmask().getLogin() + "@" + event.getUserHostmask().getHostname() +") quits " + " (" + event.getReason() + ")", event.getUser());
    }

    @Override
    public void onTopic(TopicEvent event) {
        if(!event.getUser().getNick().equalsIgnoreCase(event.getBot().getNick()))
        IRCUtils.sendRelayTopic(event.getBot(), event.getChannel(), event.getTopic());
    }
    }








