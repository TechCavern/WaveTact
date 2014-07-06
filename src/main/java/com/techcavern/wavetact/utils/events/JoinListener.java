package com.techcavern.wavetact.utils.events;

import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.PermChannel;
import com.techcavern.wavetact.utils.objects.PermUser;
import com.techcavern.wavetact.utils.objects.objectUtils.PermUserUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.KickEvent;

import java.util.concurrent.TimeUnit;

/**
 * Created by jztech101 on 7/5/14.
 */
public class JoinListener extends ListenerAdapter<PircBotX> {
    public void onJoin(JoinEvent<PircBotX> event) throws Exception {
       if(PermUtils.getAccount(event.getBot(), event.getUser()) != null){
           String account = PermUtils.getAccount(event.getBot(), event.getUser());
               if(PermUserUtils.getPermLevelChannel(event.getBot().getServerInfo().getNetwork(), account, event.getChannel().getName()) != null){
                   PermChannel channel = PermUserUtils.getPermLevelChannel(event.getBot().getServerInfo().getNetwork(), account, event.getChannel().getName());
                   if(channel.getAuto()){
                        int i = channel.getPermLevel();
                           if (i == 9001 || i == 20 || i == 15) {
                                if(event.getBot().getServerInfo().getPrefixes().contains("~")) {
                                    event.getChannel().send().owner(event.getUser());
                                }else {
                                    event.getChannel().send().op(event.getUser());
                                }
                           }else if (i == 13) {
                               if(event.getBot().getServerInfo().getPrefixes().contains("&")) {
                                   event.getChannel().send().superOp(event.getUser());
                               }else {
                                   event.getChannel().send().op(event.getUser());
                               }
                           } else if (i == 10) {
                               event.getChannel().send().op(event.getUser());
                           } else if (i == 7) {
                               if(event.getBot().getServerInfo().getPrefixes().contains("%")) {
                                   event.getChannel().send().halfOp(event.getUser());
                               }else {
                                   event.getChannel().send().voice(event.getUser());
                               }                           } else if (i == 5) {
                               event.getChannel().send().voice(event.getUser());
                           } else if (i == -1){
                               User user = event.getUser();
                               String hostmask;
                               if (user.getLogin().startsWith("~")) {
                                   hostmask = "*!*@" + user.getHostmask();
                               } else {
                                   hostmask = "*!" + user.getLogin() + "@" + user.getHostmask();
                               }
                               IRCUtils.setMode(event.getChannel(), event.getBot(), "+b ",hostmask);
                           }
                       }
                   }
           }
       }
    }

