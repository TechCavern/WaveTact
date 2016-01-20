/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetactdb.tables.Channelproperty;
import org.jooq.Record;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PartEvent;

import java.util.concurrent.TimeUnit;


/**
 * @author jztech101
 */
public class PartListener extends ListenerAdapter {
    public void onPart(PartEvent event) throws Exception {
        IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), IRCUtils.noPing(event.getUser().getNick()) + " left " + event.getChannel().getName() + " (" + event.getReason() + ")");
        Record rec = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), "removerejoin");
        if (rec == null)
            return;
        if (rec.getValue(Channelproperty.CHANNELPROPERTY.VALUE).equalsIgnoreCase("true")) {
            if (Registry.lastLeftChannel.get(event.getBot()).equals(event.getChannel().getName())) {
                Registry.lastLeftChannel.put(event.getBot(), "");
            } else if (event.getUser().getNick().equals(event.getBot().getNick())) {
                int tries = 0;
                do {
                    event.getBot().sendIRC().joinChannel(event.getChannel().getName());
                    tries++;
                    TimeUnit.SECONDS.sleep(5);
                } while (tries < 10 && !event.getBot().getUserBot().getChannels().contains(event.getChannel()));
            }
        }
    }
}
