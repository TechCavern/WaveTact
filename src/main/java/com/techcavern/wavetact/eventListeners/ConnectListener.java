/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.objects.NetProperty;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.ModeEvent;

import javax.xml.crypto.Data;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.techcavern.wavetactdb.Tables.CHANNELPROPERTY;
import static com.techcavern.wavetactdb.Tables.SERVERS;


/**
 * @author jztech101
 */
public class ConnectListener extends ListenerAdapter {

    public void onConnect(ConnectEvent event) throws Exception {
        String NickServCommand = DatabaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(event.getBot())).getValue(SERVERS.NICKSERVCOMMAND);
        String NickServNick = DatabaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(event.getBot())).getValue(SERVERS.NICKSERVNICK);
        if(NickServNick == null){
            NickServNick = "NickServ";
        }
        if(NickServCommand != null) {
            event.getBot().sendRaw().rawLine("PRIVMSG " + NickServNick + " :" + NickServCommand);
        }
        for(String channel:Arrays.asList(StringUtils.split(DatabaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(event.getBot())).getValue(SERVERS.CHANNELS), ", "))){
            Registry.MessageQueue.add(new NetProperty("JOIN :" + channel, event.getBot()));
        }
    }

}
