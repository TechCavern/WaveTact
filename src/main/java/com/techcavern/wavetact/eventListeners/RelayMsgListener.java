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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Colors;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

import static com.techcavern.wavetactdb.Tables.CHANNELPROPERTY;

/**
 * @author jztech101
 */
public class RelayMsgListener extends ListenerAdapter {
    @Override
    public void onMessage(MessageEvent event) throws Exception {
        IRCUtils.sendRelayMessage(event.getBot(),"<-" + event.getUser().getNick() +"> " + event.getMessage());
    }
    @Override
    public void onAction(ActionEvent event){
        IRCUtils.sendRelayMessage(event.getBot(), "* " + "-" + event.getUser().getNick() + event.getMessage());
    }
    @Override
    public void onKick(KickEvent event){
        IRCUtils.sendRelayMessage(event.getBot(), "-" + event.getUser().getNick() + " kicks " + event.getRecipient().getNick() + " (" + event.getReason() + ")");
    }
    @Override
    public void onPart(PartEvent event){
        IRCUtils.sendRelayMessage(event.getBot(), "-" + event.getUser().getNick() + " parts " + " (" + event.getReason() + ")");
    }
    @Override
    public void onQuit(QuitEvent event){
        IRCUtils.sendRelayMessage(event.getBot(), "-" + event.getUser().getNick() + " parts " + " (" + event.getReason() + ")");
    }
    @Override
    public void onJoin(JoinEvent event){
        IRCUtils.sendRelayMessage(event.getBot(), "-" + event.getUser().getNick() + " joins");
    }
}







