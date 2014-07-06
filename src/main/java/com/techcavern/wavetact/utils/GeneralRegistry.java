package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.fileUtils.Configuration;
import com.techcavern.wavetact.utils.logUtils.LoggingArrayList;
import com.techcavern.wavetact.utils.objects.*;
import com.techcavern.wavetact.utils.objects.objectUtils.PermUserUtils;
import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class GeneralRegistry {

    public static final List<PermUser> PermUsers = new LoggingArrayList<PermUser>("PermUser");
    public static final List<String> Controllers = new LoggingArrayList<String>("Controllers");
    public static final List<String> Authors = Arrays.asList("JZTech101", "kaendfinger", "deathcrazyuberlironman", "Leah", "Logan_");
    public static final List<Command> Commands = new LoggingArrayList<Command>("Command");
    public static final List<Command> PrivateCommands = new LoggingArrayList<Command>("PrivateCommand");
    public static final List<SimpleMessage> SimpleMessages = new LoggingArrayList<SimpleMessage>("SimpleMessages");
    public static final List<SimpleAction> SimpleActions = new LoggingArrayList<SimpleAction>("SimpleActions");
    public static final List<String> HighFives = new ArrayList<String>();
    public static final MultiBotManager<PircBotX> WaveTact = new MultiBotManager<PircBotX>();
    public static final ForkJoinPool TASKS = new ForkJoinPool();
    public static final List<Command> COMMANDS = new LinkedList<Command>();
    public static final Map<String, Configuration> configs = new HashMap<String, Configuration>();
    public static final List<UTime> BanTimes = new LoggingArrayList<UTime>("BanTimes");
    public static final List<UTime> QuietTimes = new LoggingArrayList<UTime>("QuietTimes");
    public static final List<CommandChar> CommandChars = new LoggingArrayList<CommandChar>("CommandChar");
    public static final List<CommandLine> CommandLines = new LoggingArrayList<CommandLine>("CommandLine");
    public static final List<CommandLine> CommandLineArguments = new LoggingArrayList<CommandLine>("CommandLineArguments");

}
