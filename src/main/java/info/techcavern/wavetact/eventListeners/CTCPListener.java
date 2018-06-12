/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.techcavern.wavetact.eventListeners;

import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.PermUtils;
import info.techcavern.wavetact.utils.Registry;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

import static info.techcavern.wavetactdb.Tables.NETWORKPROPERTY;

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
        class process implements Runnable {
            public void run(){
		String[] message = StringUtils.split(event.getLine(), " ");
		if(message[1].equals("PRIVMSG") && StringUtils.isAlphanumeric(message[2]) && message[3].startsWith(":\u0001") && event.getLine().endsWith("\u0001")){
                    IRCUtils.sendLogChanMsg(event.getBot(), "[PM] " + IRCUtils.noPing(message[0]).replace(":","") +": " +StringUtils.join(message, " ",3, message.length).replace(":",""));
		}
           }
        }
        Registry.threadPool.execute(new process());
    }
}




