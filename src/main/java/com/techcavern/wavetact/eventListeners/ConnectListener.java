/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.consoleCommands.network.Connect;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.CoreHooks;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.managers.ListenerManager;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.techcavern.wavetactdb.Tables.NETWORKS;


/**
 * @author jztech101
 */
public class ConnectListener extends ListenerAdapter {

    public void onConnect(ConnectEvent event) throws Exception {
        String NickServCommand = DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(event.getBot())).getValue(NETWORKS.NICKSERVCOMMAND);
        String NickServNick = DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(event.getBot())).getValue(NETWORKS.NICKSERVNICK);
        if (NickServNick == null) {
            NickServNick = "NickServ";
        }
        if (NickServCommand != null) {
            event.getBot().sendRaw().rawLine("PRIVMSG " + NickServNick + " :" + NickServCommand);
        }
        TimeUnit.SECONDS.sleep(10);
        Registry.messageQueue.get(event.getBot()).addAll(Arrays.asList(StringUtils.split(DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(event.getBot())).getValue(NETWORKS.CHANNELS), ", ")).stream().map(channel -> ("JOIN :" + channel)).collect(Collectors.toList()));
        TimeUnit.SECONDS.sleep(10);
            ListenerManager listenerManager = event.getBot().getConfiguration().getListenerManager();
            listenerManager.getListeners().stream().filter(lis -> !(lis instanceof ConnectListener || lis instanceof CoreHooks)).forEach(listener->listenerManager.removeListener(listener));
            listenerManager.addListener(new ChanMsgListener());
            listenerManager.addListener(new PartListener());
            listenerManager.addListener(new PrivMsgListener());
            listenerManager.addListener(new KickListener());
            listenerManager.addListener(new BanListener());
            listenerManager.addListener(new JoinListener());
            listenerManager.addListener(new FunMsgListener());
            listenerManager.addListener(new RelayMsgListener());
            listenerManager.addListener(new TellMsgListener());
        IRCUtils.sendLogChanMsg(event.getBot(), "[Connection Successful]");
    }

}
