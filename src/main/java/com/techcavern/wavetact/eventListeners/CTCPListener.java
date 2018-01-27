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
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import static com.techcavern.wavetactdb.Tables.NETWORKPROPERTY;

/**
 * @author jztech101
 */
public class CTCPListener extends ListenerAdapter {

    @Override
    public void onPing(PingEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                if(event.getChannel() == null){
                    IRCUtils.sendLogChanMsg(event.getBot(), "[PM] " +IRCUtils.noPing(event.getUserHostmask().getNick()) +"!" + event.getUserHostmask().getLogin()+ "@" + event.getUserHostmask().getHostname() +":PING ");
                }
            }

        }
        Registry.threadPool.execute(new process());
    }
}




