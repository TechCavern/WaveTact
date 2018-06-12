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
import info.techcavern.wavetactdb.tables.Channelproperty;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import org.jooq.Record;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.InviteEvent;
import org.pircbotx.hooks.events.JoinEvent;

import static info.techcavern.wavetactdb.Tables.CHANNELUSERPROPERTY;
import static info.techcavern.wavetactdb.Tables.NETWORKPROPERTY;
import static info.techcavern.wavetactdb.Tables.NETWORKS;


/**
 * @author jztech101
 */
public class InviteListener extends ListenerAdapter {

    @Override
    public void onInvite(InviteEvent event) throws Exception {
        IRCUtils.sendLogChanMsg(event.getBot(), "[Invite] " + IRCUtils.noPing(event.getUserHostmask().getNick()) +"!" + event.getUserHostmask().getLogin()+ "@" + event.getUserHostmask().getHostname() + ": " + event.getChannel());
        Record rec = DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "joinoninvite");
        if (rec != null && rec.getValue(NETWORKPROPERTY.VALUE).equalsIgnoreCase("true")){
            Record netRecord = DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(event.getBot()));
            netRecord.setValue(NETWORKS.CHANNELS, DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(event.getBot())).getValue(NETWORKS.CHANNELS) + ", " + event.getChannel());
            DatabaseUtils.updateNetwork(netRecord);
            Registry.messageQueue.get(event.getBot()).add("JOIN :" + event.getChannel());
        }

    }

}
