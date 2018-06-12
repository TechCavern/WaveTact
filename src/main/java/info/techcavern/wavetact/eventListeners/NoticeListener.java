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
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import static info.techcavern.wavetactdb.Tables.NETWORKPROPERTY;

/**
 * @author jztech101
 */
public class NoticeListener extends ListenerAdapter {

    @Override
    public void onNotice(NoticeEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                if(event.getChannel() != null){
                    IRCUtils.sendLogChanMsg(event.getBot(), "[Notice] " +IRCUtils.noPing(event.getUser().getNick()) +"!" + event.getUser().getLogin()+ "@" + event.getUser().getHostname()  + ": (" + event.getChannelSource() + ") " + event.getNotice());
                }else if(event.getUser() != null){
                    IRCUtils.sendLogChanMsg(event.getBot(), "[Notice] " + IRCUtils.noPing(event.getUser().getNick())+"!" + event.getUser().getLogin()+ "@" + event.getUser().getHostname() + ": " + event.getNotice());
                }else {
                    IRCUtils.sendLogChanMsg(event.getBot(), "[Notice] " + IRCUtils.noPing(event.getUserHostmask().getNick()) +"!" + event.getUserHostmask().getLogin()+ "@" + event.getUserHostmask().getHostname()+ ": " + event.getNotice());
                }
            }

        }
        Registry.threadPool.execute(new process());
    }
}




