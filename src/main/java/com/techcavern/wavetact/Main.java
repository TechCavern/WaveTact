package com.techcavern.wavetact;

import com.techcavern.wavetact.thread.CheckTime;
import com.techcavern.wavetact.utils.Configuration;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.PircBotX;
import org.slf4j.impl.SimpleLogger;

import java.io.File;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

@SuppressWarnings("ConstantConditions")
class Main {


    public static void main(String[] args) throws Exception {
        // @TODO: Move this to a class to parse them.
        for (String s : args)
        {
            if (s.equals("--help") || s.equals("-h"))
            {
                System.out.println("Help");
                System.out.println("-h or --help - Show this help screen");
                System.out.println("-m or --makeconf - Make configuration for file");
                System.exit(0);
            }
            else if (s.equals("--makeconf") || s.equals("-m"))
            {
                boolean loop = true;
                while (loop)
                {
                    Scanner input = new Scanner(System.in);
                    System.out.print("Server name: ");
                    String name = input.nextLine();
                    Configuration config = new Configuration(new File("servers/", name + ".server"));
                    config.set("name", name);
                    System.out.print("Server host: ");
                    config.set("server", input.nextLine());
                    System.out.print("Server nick: ");
                    config.set("nick", input.nextLine());
                    System.out.print("Channels(#chan1, #chan2): ");
                    config.set("channels", input.nextLine());
                    System.out.print("Nickserv Pass(empty for none): ");
                    config.set("nickserv", input.nextLine());
                    System.out.print("Command Prefix: ");
                    config.set("prefix", input.nextLine()); 
                    config.save();
                    System.out.print("Add another? [y/n]");
                    char ans = input.next().charAt(0);
                    if (!(ans == 'y'))
                        loop = false;
                }
                System.exit(0);
            }
            else
            {
                System.out.println("Invalid argument: " + s);
                System.out.println("Use -h or --help for help);
                System.exit(0);
            }
        }
        System.out.println("Starting...");
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        IRCUtils.registerCommands();
        IRCUtils.registerNetworks();
//        IRCUtils.registerDevServer();
        IRCUtils.loadSimpleActions();
        IRCUtils.loadSimpleMessages();
        IRCUtils.loadBanTimes();
        IRCUtils.loadQuietTimes();
        IRCUtils.startThreads();
        GeneralRegistry.WaveTact.start();
    }
}
