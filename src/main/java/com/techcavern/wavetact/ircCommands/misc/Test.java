package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import java.util.Timer;
import java.util.TimerTask;

//@IRCCMD
public class Test extends IRCCommand {

    public Test() {
        super(GeneralUtils.toArray("test company timer dev worldfarm"), 20, "test", "Test Command (This should not show up in a production environment. If it does, report it", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        class CrackbotListener extends ListenerAdapter {

            @Override
            public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
                if (event.getUser().getNick().equalsIgnoreCase("Crackbot")) {
                    Registry.messageQueue.get(network).add("PRIVMSG ##powder-moo :./start");
                }
            }

        }
        TimerTask buy24cow = new TimerTask() {
            public void run() {
                TimerTask sellall = new TimerTask() {
                    public void run() {
                        Registry.messageQueue.get(network).add("PRIVMSG ##powder-moo :./sellall");
                        Registry.messageQueue.get(network).add("PRIVMSG ##powder-moo :./bc24");
                    }
                };
                Registry.messageQueue.get(network).add("PRIVMSG ##powder-moo :./bc24");
                Timer timer2 = new Timer();
                timer2.schedule(sellall, 30000);
            }
        };
        TimerTask usecow = new TimerTask() {
            public void run() {
                Registry.messageQueue.get(network).add("PRIVMSG ##powder-moo :./use cow");
            }
        };
        Timer timer1 = new Timer();
        if (command.equalsIgnoreCase("company")) {
            network.getConfiguration().getListenerManager().addListener(new CrackbotListener());
            Registry.messageQueue.get(network).add("PRIVMSG ##powder-moo :./start");
        } else if (command.equalsIgnoreCase("timer")) {
            timer1.scheduleAtFixedRate(buy24cow, 0, 60000);
            timer1.scheduleAtFixedRate(usecow, 0, 3000);
        } else if (command.equalsIgnoreCase("stoptimer")) {
            timer1.purge();
        }
    }
}
