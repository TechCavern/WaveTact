package com.techcavern.wavetact.utils.events;

import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.PermChannel;
import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;

/**
 * Created by jztech101 on 7/5/14.
 */
public class JoinListener extends ListenerAdapter<PircBotX> {
    public void onJoin(JoinEvent<PircBotX> event) throws Exception {
        PermChannel PLChannel = PermChannelUtils.getPermLevelChannel(event.getBot().getServerInfo().getNetwork(), PermUtils.getAccount(event.getBot(), event.getUser().getNick()), event.getChannel().getName());
               if(PLChannel != null){
                   if(PLChannel.getAuto()){
                        int i = PLChannel.getPermLevel();
                           if (i == 9001 || i == 20 || i == 15 || i == 18) {
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


