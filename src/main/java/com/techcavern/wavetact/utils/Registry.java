package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.console.ConsoleServer;
import com.techcavern.wavetact.objects.*;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jooq.DSLContext;
import org.pircbotx.MultiBotManager;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Registry {
    public static String LastLeftChannel = "";
    public static final HashMap<String, String> QuietBans = new HashMap<>();
    public static final ForkJoinPool threadPool = new ForkJoinPool(15);
    public static final List<IRCCommand> IRCCommands = new ArrayList<>();
    public static final List<AuthedUser> AuthedUsers = new ArrayList<>();
    public static final List<FunObject> Attacks = new ArrayList<>();
    public static final List<String> Eightball = new ArrayList<>();
    public static final MultiBotManager WaveTact = new MultiBotManager();
    public static final List<ConsoleCommand> ConsoleCommands = new ArrayList<>();
    public static final StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
    public static final Reflections wavetactreflection = new Reflections("com.techcavern.wavetact");
    public static final List<NetProperty> NetworkName = new ArrayList<>();
    public static String wundergroundapikey = DatabaseUtils.getConfig("wundergroundapikey");
    public static String wolframalphaapikey = DatabaseUtils.getConfig("wolframalphaapikey");
    public static String wordnikapikey = DatabaseUtils.getConfig("wordnikapikey");
    ;
    public static String googleapikey = DatabaseUtils.getConfig("googleapikey");
    ;
    public static ConsoleServer consoleServer = new ConsoleServer();
    public static DSLContext WaveTactDB = null;
}
