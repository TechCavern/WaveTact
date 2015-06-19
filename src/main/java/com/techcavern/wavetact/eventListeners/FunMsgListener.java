/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Colors;
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import static com.techcavern.wavetactdb.Tables.CHANNELUSERPROPERTY;

/**
 * @author jztech101
 */
public class FunMsgListener extends ListenerAdapter {
    @Override
    public void onMessage(MessageEvent event) throws Exception {
        Record rec = DatabaseUtils.getChannelUserProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), PermUtils.authUser(event.getBot(), event.getUser().getNick()),"relaybotsplit");
        if(rec == null){
            if(event.getMessage().contains("http://stop-irc-bullying.eu/stop")){
                if(IRCUtils.checkIfCanKick(event.getChannel(),event.getBot(),event.getUser() )){
                    event.getChannel().send().kick(event.getUser(), "┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻");
                } else {
                    IRCUtils.sendAction(event.getUser(), event.getBot(), event.getChannel(), "kicks " + event.getUser().getNick() + " (┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻)", "");
                }
            }
        }
    }
}







