package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.WhoisEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
//@IRCCMD
public class Test extends IRCCommand {

    public Test() {
        super(GeneralUtils.toArray("test test5 timer project refillcash "), 0, "test", "moooo", false);
    }
    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
       /**
        class CrackbotListener extends ListenerAdapter {
            @Override
            public void onMessage(MessageEvent event) throws Exception {
                if (event.getUser().getNick().equalsIgnoreCase("JZTech1O1") && event.getChannel().getName().equals("#tctest") && event.getMessage().contains("Anna_28")) {
                    IRCUtils.sendMessage(event.getUser(), event.getBot(), event.getChannel(), "Moo", prefix);
                }
            }
        }
        network.getConfiguration().getListenerManager().addListener(new CrackbotListener());
        **/
        /**
        TimerTask buy = new TimerTask() {
            public void run() {
                TimerTask sellall = new TimerTask() {
                    public void run() {
                        IRCUtils.sendMessage(user, network, channel, "./sellall", prefix);
                //        IRCUtils.sendMessage(user, network, channel, "./buy cow <<calc 24-$inv[cow]>>", prefix);
                        IRCUtils.sendMessage(user, network, channel, "./buy company <<calc 20-$inv[company]>>", prefix);
                    }
                };
           //     IRCUtils.sendMessage(user, network, channel, "./buy cow <<calc 24-$inv[cow]>>", prefix);
                IRCUtils.sendMessage(user, network, channel, "./buy company <<calc 20-$inv[company]>>", prefix);
                Timer timer2 = new Timer();
                timer2.schedule(sellall, 40000);
            }
        };
        TimerTask use = new TimerTask() {
            public void run() {
             //   IRCUtils.sendMessage(user, network, channel, "./use cow", prefix);
                IRCUtils.sendMessage(user, network, channel, "./use company", prefix);
                IRCUtils.sendMessage(user, network, channel, "./use company", prefix);
            }
        };
        TimerTask givejz = new TimerTask() {
            public void run() {
                IRCUtils.sendMessage(user, network, channel, "./give JZTech1O1 <<calc $cash-4000000000000>>", prefix);
            }
        };
        TimerTask refillcash = new TimerTask() {
            public void run() {
                IRCUtils.sendMessage(user, network, channel, "./give WTTest3 4000000000000", prefix);
            }
        };
        Timer timer1 = new Timer();
        if (command.equalsIgnoreCase("project")) {
            network.getConfiguration().getListenerManager().addListener(new CrackbotListener());
            IRCUtils.sendMessage(user, network, channel, "./start", prefix);
        } else if (command.equalsIgnoreCase("timer")) {
            timer1.scheduleAtFixedRate(buy, 0, 40000);
            timer1.scheduleAtFixedRate(use, 0, 4000);
            timer1.scheduleAtFixedRate(givejz, 0, 400000);
        } else if (command.equalsIgnoreCase("stoptimer")) {
            timer1.purge();
        } else if (command.equalsIgnoreCase("refillcash")) {
            timer1.scheduleAtFixedRate(refillcash, 0, 500000);
        }else{
        }
         **/
        int j = Integer.valueOf(args[0]);
        args = ArrayUtils.remove(args,0);
        for(int i =0; i<j; i++){
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(args, " "), prefix);
        }
    }
}
