package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.WhoisEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@IRCCMD
public class Test extends IRCCommand {

    public Test() {
        super(GeneralUtils.toArray("test timer project refillcash"), 0, "test", "moooo", false);
    }
    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        class CrackbotListener extends ListenerAdapter {
            @Override
            public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
                if (event.getUser().getNick().equalsIgnoreCase("Crackbot")) {
                    IRCUtils.sendMessage(user, network, channel, "./start", prefix);
                }
            }
        }
        TimerTask buy = new TimerTask() {
            public void run() {
                TimerTask sellall = new TimerTask() {
                    public void run() {
                        IRCUtils.sendMessage(user, network, channel, "./sellall", prefix);
                        IRCUtils.sendMessage(user, network, channel, "./buy cow <<calc 48-$inv[cow]>>", prefix);
                        IRCUtils.sendMessage(user, network, channel, "./buy company <<calc 20-$inv[company]>>", prefix);
                    }
                };
                IRCUtils.sendMessage(user, network, channel, "./buy cow <<calc 48-$inv[cow]>>", prefix);
                IRCUtils.sendMessage(user, network, channel, "./buy company <<calc 20-$inv[company]>>", prefix);
                Timer timer2 = new Timer();
                timer2.schedule(sellall, 40000);
            }
        };
        TimerTask use = new TimerTask() {
            public void run() {
                IRCUtils.sendMessage(user, network, channel, "./use cow", prefix);
                IRCUtils.sendMessage(user, network, channel, "./use company", prefix);
            }
        };
        TimerTask givejz = new TimerTask() {
            public void run() {
                IRCUtils.sendMessage(user, network, channel, "./give WTTest <<calc $cash-4000000000000>>", prefix);
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
            timer1.scheduleAtFixedRate(buy, 0, 80000);
            timer1.scheduleAtFixedRate(use, 0, 4000);
            timer1.scheduleAtFixedRate(givejz, 0, 800000);
        } else if (command.equalsIgnoreCase("stoptimer")) {
            timer1.purge();
        } else if (command.equalsIgnoreCase("refillcash")) {
            timer1.scheduleAtFixedRate(refillcash, 0, 1000000);
        }
    }
}
