package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.fileUtils.Configuration;
import com.techcavern.wavetact.utils.logUtils.LoggingArrayList;
import com.techcavern.wavetact.utils.objects.*;
import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class GeneralRegistry {

    public static final List<PermChannel> PermChannels = new LoggingArrayList<PermChannel>("PermChannel");
    public static final List<UTime> Topic = new LoggingArrayList<UTime>("Topic") ;
    public static final List<String> Controllers = new LoggingArrayList<String>("Controllers");
    public static final List<String> Globals = new LoggingArrayList<String>("Globals");
    public static final List<String> Authors = Arrays.asList("JZTech101", "kaendfinger", "deathcrazyuberlironman", "Leah", "Logan_");
    public static final List<GenericCommand> GenericCommands = new LoggingArrayList<GenericCommand>("Command");
    public static final List<GenericCommand> TrustedCommands = new LoggingArrayList<GenericCommand>("TrustedCommand");
    public static final List<GenericCommand> ControllerCommands = new LoggingArrayList<GenericCommand>("ControllerCommand");
    public static final List<GenericCommand> ChanOwnerCommands = new LoggingArrayList<GenericCommand>("ChanOwnerCommand");
    public static final List<GenericCommand> ChanHalfOpCommands = new LoggingArrayList<GenericCommand>("ChanHalfOpCommand");
    public static final List<GenericCommand> AnonymonityCommands = new LoggingArrayList<GenericCommand>("AnonymonityCommand");

    public static final List<GenericCommand> ChanOpCommands = new LoggingArrayList<GenericCommand>("ChanOpCommand");
    public static final List<GenericCommand> ChanFounderCommands = new LoggingArrayList<GenericCommand>("ChanFounderCommand");
    public static final List<SimpleMessage> SimpleMessages = new LoggingArrayList<SimpleMessage>("SimpleMessages");
    public static final List<SimpleAction> SimpleActions = new LoggingArrayList<SimpleAction>("SimpleActions");
    public static final List<String> HighFives = new ArrayList<String>();
    public static final MultiBotManager<PircBotX> WaveTact = new MultiBotManager<PircBotX>();
    public static final ForkJoinPool TASKS = new ForkJoinPool();
    public static final List<GenericCommand> COMMANDS = new LinkedList<GenericCommand>();
    public static final Map<String, Configuration> configs = new HashMap<String, Configuration>();
    public static final List<UTime> BanTimes = new LoggingArrayList<UTime>("BanTimes");
    public static final List<UTime> QuietTimes = new LoggingArrayList<UTime>("QuietTimes");
    public static final List<CommandChar> CommandChars = new LoggingArrayList<CommandChar>("CommandChar");
    public static final List<CommandLine> CommandLines = new LoggingArrayList<CommandLine>("CommandLine");
    public static final List<CommandLine> CommandLineArguments = new LoggingArrayList<CommandLine>("CommandLineArguments");

}
