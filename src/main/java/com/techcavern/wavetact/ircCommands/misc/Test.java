package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.WhoisEvent;

import java.util.HashMap;
import java.util.Map;

//@IRCCMD
public class Test extends IRCCommand {

    public Test() {
        super(GeneralUtils.toArray("test"), 0, "test", "moooo", false);
    }
    public static int  boo = 0;
    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        class RunOne implements Runnable {
            public void run() {
                runloop(1000000000L,2999999999L, user, network,channel,prefix);
            }
        }
        class RunTwo implements Runnable {
            public void run() {
                runloop(3000000000L,4999999999L, user, network,channel,prefix);
            }
        }
        class RunThree implements Runnable {
            public void run() {
                runloop(5000000000L,6999999999L, user, network,channel,prefix);
            }
        }
        class RunFour implements Runnable {
            public void run() {
                runloop(7000000000L,8999999999L, user, network,channel,prefix);
            }
        }
        class RunFive implements Runnable {
            public void run() {
                runloop(9000000000L,9999999999L, user, network,channel,prefix);
            }
        }
        Registry.threadPool.execute(new RunOne());
        Registry.threadPool.execute(new RunTwo());
        Registry.threadPool.execute(new RunThree());
        Registry.threadPool.execute(new RunFour());
        Registry.threadPool.execute(new RunFive());
        System.out.println("Finished. Found a grand total of " + boo);
    }
    public static void runloop(long a, long d, User user, PircBotX network, Channel channel, String prefix){
        System.out.println("Starting...");

        loop:
        for (long i = a; i < d; i++) {
            char[] c = Long.toString(i).toCharArray();
            for (int j = 0; j < c.length; j++) {
                for (int k = j + 1; k < c.length; k++) {
                    if (c[j] == c[k]) {
                        //  IRCUtils.sendMessage(user, network, channel, c[j] + " "  + c[k], prefix);
                        continue loop;
                    }
                }
            }
            //     IRCUtils.sendMessage(user, network, channel, , prefix);
            System.out.println("Found One! " + i + " Count: " + boo);
            boo++;
        }
        System.out.println("Finished. Found a grand total of " + boo);
    }
}
