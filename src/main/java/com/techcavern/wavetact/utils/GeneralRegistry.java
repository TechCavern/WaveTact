package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.fileUtils.Configuration;
import com.techcavern.wavetact.utils.objects.*;
import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class GeneralRegistry {

    public static final List<PermChannel> PermChannels = new ArrayList<PermChannel>();
    public static final List<UTime> Topic = new ArrayList<UTime>() ;
    public static final List<String> Controllers = new ArrayList<String>();
    public static final List<Global> Globals = new ArrayList<Global>();
    public static final List<GenericCommand> AllCommands = new ArrayList<GenericCommand>();
    public static final List<GenericCommand> GenericCommands = new ArrayList<GenericCommand>();
    public static final List<GenericCommand> TrustedCommands = new ArrayList<GenericCommand>();
    public static final List<GenericCommand> ControllerCommands = new ArrayList<GenericCommand>();
    public static final List<GenericCommand> GlobalCommands = new ArrayList<GenericCommand>();
    public static final List<GenericCommand> ChanOwnerCommands = new ArrayList<GenericCommand>();
    public static final List<GenericCommand> ChanHalfOpCommands = new ArrayList<GenericCommand>();
    public static final List<GenericCommand> AnonymonityCommands = new ArrayList<GenericCommand>();
    public static final List<FunObject> Attacks = new ArrayList<FunObject>();
    public static final List<GenericCommand> ChanOpCommands = new ArrayList<GenericCommand>();
    public static final List<GenericCommand> ChanFounderCommands = new ArrayList<GenericCommand>();
    public static final List<SimpleMessage> SimpleMessages = new ArrayList<SimpleMessage>();
    public static final List<SimpleAction> SimpleActions = new ArrayList<SimpleAction>();
    public static final MultiBotManager<PircBotX> WaveTact = new MultiBotManager<PircBotX>();
    public static final Map<String, Configuration> configs = new HashMap<String, Configuration>();
    public static final List<UTime> BanTimes = new ArrayList<UTime>();
    public static final List<UTime> QuietTimes = new ArrayList<UTime>();
    public static final List<CommandChar> CommandChars = new ArrayList<CommandChar>();
    public static final List<CommandLine> CommandLines = new ArrayList<CommandLine>();
    public static final List<CommandLine> CommandLineArguments = new ArrayList<CommandLine>();
    public static final List<String> GenericListCommands = new ArrayList<String>();
    public static final List<String>  TrustedListCommands = new ArrayList<String>();
    public static final List<String>  ControllerListCommands = new ArrayList<String>();
    public static final List<String>  ChanOwnerListCommands = new ArrayList<String>();
    public static final List<String>  GlobalListCommands = new ArrayList<String>();
    public static final List<String>  ChanHalfOpListCommands = new ArrayList<String>();
    public static final List<String>  ChanOpListCommands = new ArrayList<String>();
    public static final List<String>  ChanFounderListCommands = new ArrayList<String>();
}
