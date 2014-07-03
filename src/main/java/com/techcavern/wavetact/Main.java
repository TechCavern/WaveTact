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

@SuppressWarnings("ConstantConditions")
class Main {

    private static Map<String, Configuration> configs = new HashMap<>();

    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        IRCUtils.registerCommands();
        IRCUtils.loadSimpleActions();
        IRCUtils.loadSimpleMessages();
        IRCUtils.loadBanTimes();
        IRCUtils.loadQuietTimes();
        IRCUtils.startThreads();

        File serversFolder = new File("servers/");
        serversFolder.mkdir();
        File[] files = serversFolder.listFiles();

        String name;
        Configuration config;
        for (File f : files)
        {
            if (!f.isDirectory())
            {
                name = f.getName();
                name = name.substring(0, f.getName().lastIndexOf('.'));
                config = new Configuration(f);
                configs.put(name, config);
            }
        }

        PircBotX bot;
        LinkedList<String> chans = new LinkedList<String>();
        for (Configuration c : configs.values())
        {
            for (String s : c.getString("channels").split(", "))
                chans.add(s);
            bot = IRCUtils.createbot(c.getString("nickserv"), c.getString("name"), chans,c.getString("nick"), c.getString("server"));
            GeneralRegistry.WaveTact.addBot(bot);
        }

        // TODO: @JZTech101: Port these over to a servers/SERVER.server file. Ensure they have an extension(doesn't matter what it is, just ensure they do).
        /*
        PircBotX Ovd = IRCUtils.createbot(pwd, "Ovd", GeneralRegistry.OvdChannels, GeneralRegistry.OvdNick, GeneralRegistry.OvdServer);
        PircBotX Esper = IRCUtils.createbot(g, "Esper", GeneralRegistry.EsperChannels, GeneralRegistry.EsperNick, GeneralRegistry.EsperServer);
        PircBotX ECode = IRCUtils.createbot(g, "ECode", GeneralRegistry.ECodeChannels, GeneralRegistry.ECodeNick, GeneralRegistry.ECodeServer);
        PircBotX Xertion = IRCUtils.createbot(pwd, "Xertion", GeneralRegistry.XertionChannels, GeneralRegistry.XertionNick, GeneralRegistry.XertionServer);
        PircBotX Obsidian = IRCUtils.createbot(pwd, "Obsidian", GeneralRegistry.ObsidianChannels, GeneralRegistry.ObsidianNick, GeneralRegistry.ObsidianServer);
        PircBotX Freenode = IRCUtils.createbot(g, "Freenode", GeneralRegistry.FreenodeChannels, GeneralRegistry.FreenodeNick, GeneralRegistry.FreenodeServer);

        GeneralRegistry.WaveTact.addBot(Obsidian);
        GeneralRegistry.WaveTact.addBot(Esper);
        GeneralRegistry.WaveTact.addBot(Freenode);
        GeneralRegistry.WaveTact.addBot(Ovd);
        GeneralRegistry.WaveTact.addBot(Xertion);
        GeneralRegistry.WaveTact.addBot(ECode);
*/
        //Development Server
        /*
        PircBotX Dev = IRCUtils.createbot(pwd, "Dev", GeneralRegistry.DevChannels, GeneralRegistry.DevNick, GeneralRegistry.DevServer);
        GeneralRegistry.WaveTact.addBot(Dev);
        */
        GeneralRegistry.WaveTact.start();
    }
}
