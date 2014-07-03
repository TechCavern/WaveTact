package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.objects.*;
import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class GeneralRegistry {

    public static final String CommandChar = "&";
    public static final List<String> Controllers = Arrays.asList("jztech101", "archtikz", "kaendfinger", "deathcrazyuberlironman", "leah");
    public static final List<String> ControllerHostmasks = Arrays.asList("*!jztech101@techcavern.com");
    public static final String OvdServer = "irc.overdrive.pw";
    public static final List<String> OvdChannels = Arrays.asList("#techcavern", "#dev");
    public static final String OvdNick = "WaveTact";
    public static final String EsperServer = "irc.esper.net";
    public static final String EsperNick = "WaveTact";
    public static final List<String> EsperChannels = Arrays.asList("#directcode", "#techcavern", "#speakeasy", "#kingrunes");
    public static final String ECodeServer = "irc.electrocode.net";
    public static final List<String> ECodeChannels = Arrays.asList("#techcavern", "#bots", "#chat");
    public static final String ECodeNick = "WaveTact";
    public static final String XertionServer = "irc.xertion.org";
    public static final List<String> XertionChannels = Arrays.asList("#techcavern");
    public static final String XertionNick = "WaveTact";
    public static final String ObsidianServer = "irc.obsidianirc.net";
    public static final List<String> ObsidianChannels = Arrays.asList("#techcavern", "#szsocial");
    public static final String ObsidianNick = "WaveTact";
    public static final String FreenodeServer = "irc.freenode.net";
    public static final List<String> FreenodeChannels = Arrays.asList("#techcavern", "##powder-bots", "#directcode", "##neobotwar");
    public static final String FreenodeNick = "WaveTact";
    public static final List<Command> Commands = new LoggingArrayList<Command>("Command");
    public static final List<SimpleMessage> SimpleMessages = new LoggingArrayList<SimpleMessage>("SimpleMessages");
    public static final List<SimpleAction> SimpleActions = new LoggingArrayList<SimpleAction>("SimpleActions");
    public static final List<String> HighFives = new ArrayList<String>();
    public static final MultiBotManager<PircBotX> WaveTact = new MultiBotManager<PircBotX>();
    public static final ForkJoinPool TASKS = new ForkJoinPool();
    public static final List<Command> COMMANDS = new LinkedList<Command>();
    public static final List<UTime> BanTimes = new LoggingArrayList<UTime>("BanTimes");
    public static final List<UTime> QuietTimes = new LoggingArrayList<UTime>("QuietTimes");


    //Development Variables
    /**
    public static final String CommandChar = "@";
    public static final String DevServer = "irc.esper.net";
    public static final List<String> DevChannels = Arrays.asList("#TechCavern");
    public static final String DevNick = "WaveTactDev";
    **/
}
