package com.techcavern.wavetact;

import com.techcavern.wavetact.thread.CheckTime;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.PircBotX;
import org.slf4j.impl.SimpleLogger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@SuppressWarnings("ConstantConditions")
class Main {


    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        String pwd = null;
        String g = args[0];
        IRCUtils.registerCommands();
        IRCUtils.loadSimpleActions();
        IRCUtils.loadSimpleMessages();
        IRCUtils.loadBanTimes();
        IRCUtils.loadQuietTimes();
        IRCUtils.startThreads();
///**
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
//**/
        //Development Server
        /**
        PircBotX Dev = IRCUtils.createbot(pwd, "Dev", GeneralRegistry.DevChannels, GeneralRegistry.DevNick, GeneralRegistry.DevServer);
        GeneralRegistry.WaveTact.addBot(Dev);
        **/
        GeneralRegistry.WaveTact.start();
    }
}
