package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.fileUtils.Configuration;
import com.techcavern.wavetact.utils.objects.*;
import org.pircbotz.MultiBotManager;
import org.pircbotz.PircBotZ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GeneralRegistry {

    public static final List<PermChannel> PermChannels = new ArrayList<>();
    public static final List<UTime> Topic = new ArrayList<>();
    public static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 100, 1,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());
    public static final List<String> Controllers = new ArrayList<>();
    public static final List<Global> Globals = new ArrayList<>();
    public static final List<GenericCommand> AllCommands = new ArrayList<>();
    public static final List<GenericCommand> GenericCommands = new ArrayList<>();
    public static final List<GenericCommand> TrustedCommands = new ArrayList<>();
    public static final List<GenericCommand> ControllerCommands = new ArrayList<>();
    public static final List<GenericCommand> GlobalCommands = new ArrayList<>();
    public static final List<GenericCommand> ChanOwnerCommands = new ArrayList<>();
    public static final List<GenericCommand> ChanHalfOpCommands = new ArrayList<>();
    public static final List<GenericCommand> FunCommands = new ArrayList<>();
    public static final List<GenericCommand> AnonymonityCommands = new ArrayList<>();
    public static final List<FunObject> Attacks = new ArrayList<>();
    public static final List<GenericCommand> ChanOpCommands = new ArrayList<>();
    public static final List<GenericCommand> ChanFounderCommands = new ArrayList<>();
    public static final List<SimpleMessage> SimpleMessages = new ArrayList<>();
    public static final List<SimpleAction> SimpleActions = new ArrayList<>();
    public static final MultiBotManager WaveTact = new MultiBotManager();
    public static final Map<String, Configuration> configs = new HashMap<>();
    public static final List<UTime> BanTimes = new ArrayList<>();
    public static final List<UTime> QuietTimes = new ArrayList<>();
    public static final List<CommandChar> CommandChars = new ArrayList<>();
    public static final List<CommandLine> CommandLines = new ArrayList<>();
    public static final List<CommandLine> CommandLineArguments = new ArrayList<>();
    public static final List<String> GenericListCommands = new ArrayList<>();
    public static final List<String> TrustedListCommands = new ArrayList<>();
    public static final List<String> ControllerListCommands = new ArrayList<>();
    public static final List<String> ChanOwnerListCommands = new ArrayList<>();
    public static final List<String> GlobalListCommands = new ArrayList<>();
    public static final List<String> ChanHalfOpListCommands = new ArrayList<>();
    public static final List<String> ChanOpListCommands = new ArrayList<>();
    public static final List<String> ChanFounderListCommands = new ArrayList<>();
}
