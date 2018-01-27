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
import org.pircbotx.hooks.events.*;

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
                    IRCUtils.sendLogChanMsg(event.getBot(), "[PM] " +IRCUtils.noPing(event.getUserHostmask().getNick()) +"!" + event.getUserHostmask().getLogin()+ "@" + event.getUserHostmask().getHostname() +": PING " + event.getPingValue());
                }
            }

        }
        Registry.threadPool.execute(new process());
    }
        @Override
    public void onTime(TimeEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                if(event.getChannel() == null){
                    IRCUtils.sendLogChanMsg(event.getBot(), "[PM] " +IRCUtils.noPing(event.getUserHostmask().getNick()) +"!" + event.getUserHostmask().getLogin()+ "@" + event.getUserHostmask().getHostname() +": TIME ");
                }
            }

        }
        Registry.threadPool.execute(new process());
    }    @Override
    public void onFinger(FingerEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                if(event.getChannel() == null){
                    IRCUtils.sendLogChanMsg(event.getBot(), "[PM] " +IRCUtils.noPing(event.getUserHostmask().getNick()) +"!" + event.getUserHostmask().getLogin()+ "@" + event.getUserHostmask().getHostname() +": FINGER ");
                }
            }

        }
        Registry.threadPool.execute(new process());
    }    @Override
    public void onAction(ActionEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                if(event.getChannel() == null){
                    IRCUtils.sendLogChanMsg(event.getBot(), "[PM] " +IRCUtils.noPing(event.getUserHostmask().getNick()) +"!" + event.getUserHostmask().getLogin()+ "@" + event.getUserHostmask().getHostname() +": ACTION " + event.getMessage());
                }
            }

        }
        Registry.threadPool.execute(new process());
    }    @Override
    public void onVersion(VersionEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                if(event.getChannel() == null){
                    IRCUtils.sendLogChanMsg(event.getBot(), "[PM] " +IRCUtils.noPing(event.getUserHostmask().getNick()) +"!" + event.getUserHostmask().getLogin()+ "@" + event.getUserHostmask().getHostname() +": VERSION ");
                }
            }

        }
        Registry.threadPool.execute(new process());
    }
        @Override
    public void onUnknown(UnknownEvent event) throws Exception {
	System.out.println(event.getLine());
        class process implements Runnable {
            public void run(){
		String[] message = StringUtils.split(event.getLine(), " ");
		if(message[1] == "PRIVMSG" && StringUtils.isAlphanumeric(message[2]) && message[3].startsWith(":\u0001") && message[3].endsWith("\u0001")){
                    IRCUtils.sendLogChanMsg(event.getBot(), "[PM] " + IRCUtils.noPing(message[0]).replace(":","") +": " +message[3].replace("\u0001",""));
		}
           }
        }
        Registry.threadPool.execute(new process());
    }
}




