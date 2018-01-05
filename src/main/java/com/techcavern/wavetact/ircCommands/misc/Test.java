package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.WhoisEvent;

import java.util.*;

import static com.techcavern.wavetactdb.Tables.RELAYS;

@IRCCMD
public class Test extends IRCCommand {

    public Test() {
        super(GeneralUtils.toArray("test test5 timer timer2 project refillcash "), 20, "test", "moooo", false);
    }
    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
       /**
        class CrackbotListener extends ListenerAdapter {
            @Override
            public void onMessage(MessageEvent event) throws Exception {
                if (event.getUser().getNick().equalsIgnoreCase("JZTech1O1") && event.getChannel().getName().equals("#tctest") && event.getMessage().contains("Moo")) {
                    IRCUtils.sendMessage(event.getUser(), event.getBot(), event.getChannel(), "Moo", prefix);
                }
            }
        }
        network.getConfiguration().getListenerManager().addListener(new CrackbotListener());

        TimerTask buy = new TimerTask() {
            public void run() {
                TimerTask sellall = new TimerTask() {
                    public void run() {
                        IRCUtils.sendMessage(user, network, channel, "*store sellall", prefix);
                  //      IRCUtils.sendMessage(user, network, channel, "./store sellall", prefix);

                        //        IRCUtils.sendMessage(user, network, channel, "*buy cow <<calc 24-$inv[cow]>>", prefix);
                        IRCUtils.sendMessage(user, network, channel, "*store buy company <<calc 20-$inv[company]>>", prefix);
                //        IRCUtils.sendMessage(user, network, channel, "./store buy company <<calc 20-$inv[company]>>", prefix);

                    }
                };
           //     IRCUtils.sendMessage(user, network, channel, "*buy cow <<calc 24-$inv[cow]>>", prefix);
                IRCUtils.sendMessage(user, network, channel, "*store buy company <<calc 20-$inv[company]>>", prefix);
              //  IRCUtils.sendMessage(user, network, channel, "./store buy company <<calc 20-$inv[company]>>", prefix);

                Timer timer2 = new Timer();
                timer2.schedule(sellall, 40000);
            }
        };
        TimerTask use = new TimerTask() {
            public void run() {
             //   IRCUtils.sendMessage(user, network, channel, "*use cow", prefix);
                IRCUtils.sendMessage(user, network, channel, "*use company", prefix);
                IRCUtils.sendMessage(user, network, channel, "*use company", prefix);
               // IRCUtils.sendMessage(user, network, channel, "./use company", prefix);
               // IRCUtils.sendMessage(user, network, channel, "./use company", prefix);

            }
        };
        TimerTask givejz = new TimerTask() {
            public void run() {
                IRCUtils.sendMessage(user, network, channel, "*give JZTech101 <<calc $cash-4000000000000>>", prefix);
             //   IRCUtils.sendMessage(user, network, channel, "./give JZTech101 <<calc $cash-4000000000000>>", prefix);

            }
        };
        TimerTask buy2 = new TimerTask() {
            public void run() {
                TimerTask sellall = new TimerTask() {
                    public void run() {
                   //     IRCUtils.sendMessage(user, network, channel, "*store sellall", prefix);
                        IRCUtils.sendMessage(user, network, channel, "./store sellall", prefix);

                        //        IRCUtils.sendMessage(user, network, channel, "*buy cow <<calc 24-$inv[cow]>>", prefix);
                 //       IRCUtils.sendMessage(user, network, channel, "*store buy company <<calc 20-$inv[company]>>", prefix);
                        IRCUtils.sendMessage(user, network, channel, "./store buy company <<calc 20-$inv[company]>>", prefix);

                    }
                };
                //     IRCUtils.sendMessage(user, network, channel, "*buy cow <<calc 24-$inv[cow]>>", prefix);
               // IRCUtils.sendMessage(user, network, channel, "*store buy company <<calc 20-$inv[company]>>", prefix);
                IRCUtils.sendMessage(user, network, channel, "./store buy company <<calc 20-$inv[company]>>", prefix);

                Timer timer2 = new Timer();
                timer2.schedule(sellall, 40000);
            }
        };
        TimerTask use2 = new TimerTask() {
            public void run() {
                //   IRCUtils.sendMessage(user, network, channel, "*use cow", prefix);
              //  IRCUtils.sendMessage(user, network, channel, "*use company", prefix);
             //   IRCUtils.sendMessage(user, network, channel, "*use company", prefix);
                IRCUtils.sendMessage(user, network, channel, "./use company", prefix);
                IRCUtils.sendMessage(user, network, channel, "./use company", prefix);

            }
        };
        TimerTask givejz2 = new TimerTask() {
            public void run() {
             //   IRCUtils.sendMessage(user, network, channel, "*give JZTech101 <<calc $cash-4000000000000>>", prefix);
                IRCUtils.sendMessage(user, network, channel, "./give JZTech101 <<calc $cash-4000000000000>>", prefix);

            }
        };
        TimerTask refillcash = new TimerTask() {
            public void run() {
                IRCUtils.sendMessage(user, network, channel, "*give WTTest3 4000000000000", prefix);
            }
        };
        Timer timer1 = new Timer();
        if (command.equalsIgnoreCase("project")) {
            network.getConfiguration().getListenerManager().addListener(new CrackbotListener());
            IRCUtils.sendMessage(user, network, channel, "*start", prefix);
        } else if (command.equalsIgnoreCase("timer")) {
            timer1.scheduleAtFixedRate(buy, 0, 300000);
            timer1.scheduleAtFixedRate(use, 0, 30000);
            timer1.scheduleAtFixedRate(givejz, 0, 3000000);
        }else if (command.equalsIgnoreCase("timer2")) {
            timer1.scheduleAtFixedRate(buy2, 0, 300000);
            timer1.scheduleAtFixedRate(use2, 0, 30000);
            timer1.scheduleAtFixedRate(givejz2, 0, 3000000);
        } else if (command.equalsIgnoreCase("stoptimer")) {
            timer1.purge();
        } else if (command.equalsIgnoreCase("refillcash")) {
            timer1.scheduleAtFixedRate(refillcash, 0, 500000);
        }else{
        }
        **/
        /**
        int j = Integer.valueOf(args[0]);
        args = ArrayUtils.remove(args,0);
        for(int i =0; i<j; i++){
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(args, " "), prefix);
        }
         **/
        /**
        Set<String> networks = new HashSet<>();
        for (Record e : DatabaseUtils.getRelays()) {
            networks.add(e.getValue(RELAYS.PROPERTY) + ": " + e.getValue(RELAYS.VALUE));
        }

        IRCUtils.sendMessage(user, network, channel, StringUtils.join(networks, ", "), prefix);
         **/
   //     commandIO.getPrintStream().println(StringUtils.join(networks, ", "));
    }
}
