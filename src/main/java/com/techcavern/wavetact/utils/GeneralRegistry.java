package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.objects.*;
import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class GeneralRegistry {

    public static final List<String> Controllers = Arrays.asList("jztech101", "archtikz", "kaendfinger", "deathcrazyuberlironman", "leah");
    public static final List<String> ControllerHostmasks = Arrays.asList("*!jztech101@techcavern.com");
    public static final List<Command> Commands = new LoggingArrayList<>("Command");
    public static final List<SimpleMessage> SimpleMessages = new LoggingArrayList<>("SimpleMessages");
    public static final List<SimpleAction> SimpleActions = new LoggingArrayList<>("SimpleActions");
    public static final List<String> HighFives = new ArrayList<>();
    public static final MultiBotManager<PircBotX> WaveTact = new MultiBotManager<>();
    public static final ForkJoinPool TASKS = new ForkJoinPool();
    public static final List<Command> COMMANDS = new LinkedList<>();
    public static final Map<String, Configuration> configs = new HashMap<>();
    public static final List<UTime> BanTimes = new LoggingArrayList<>("BanTimes");
    public static final List<UTime> QuietTimes = new LoggingArrayList<>("QuietTimes");
    public static final List<CommandChar> CommandChars= new LoggingArrayList<>("CommandChar");
    public static final List<CommandLine> CommandLines = new LoggingArrayList<>("CommandLine");


    //Development Variables
    /**
    public static final String CommandChar = "@";
    public static final String DevServer = "irc.esper.net";
    public static final List<String> DevChannels = Arrays.asList("#TechCavern");
    public static final String DevNick = "WaveTactDev";
    **/
}
