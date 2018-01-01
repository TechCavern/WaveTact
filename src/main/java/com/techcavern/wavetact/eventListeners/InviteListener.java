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
import com.techcavern.wavetactdb.tables.Channelproperty;
import org.jooq.Record;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.InviteEvent;
import org.pircbotx.hooks.events.JoinEvent;

import static com.techcavern.wavetactdb.Tables.CHANNELUSERPROPERTY;
import static com.techcavern.wavetactdb.Tables.NETWORKPROPERTY;
import static com.techcavern.wavetactdb.Tables.NETWORKS;


/**
 * @author jztech101
 */
public class InviteListener extends ListenerAdapter {

    @Override
    public void onInvite(InviteEvent event) throws Exception {
        IRCUtils.sendLogChanMsg(event.getBot(), "[INVITE] " + event.getChannel() + " by " + IRCUtils.noPing(event.getUserHostmask().getNick()));
        Record rec = DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "joinoninvite");
        if (rec != null && rec.getValue(NETWORKPROPERTY.VALUE).equalsIgnoreCase("true")){
            Record netRecord = DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(event.getBot()));
            netRecord.setValue(NETWORKS.CHANNELS, DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(event.getBot())).getValue(NETWORKS.CHANNELS) + ", " + event.getChannel());
            DatabaseUtils.updateNetwork(netRecord);
            Registry.messageQueue.get(event.getBot()).add("JOIN :" + event.getChannel());
        }

    }

}
