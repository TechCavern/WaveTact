package com.techcavern.wavetact.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public static final List<ChannelProperty> Topic = new ArrayList<>();
    public static final ForkJoinPool threadPool = new ForkJoinPool(15);
    public static final List<NetworkAdmin> NetworkAdmins = new ArrayList<>();
    public static final List<IRCCommand> AllCommands = new ArrayList<>();
    public static final List<IRCCommand> GenericIRCCommands = new ArrayList<>();
    public static final List<IRCCommand> TrustedCommands = new ArrayList<>();
    public static final List<IRCCommand> ControllerCommands = new ArrayList<>();
    public static final List<IRCCommand> NetAdminCommands = new ArrayList<>();
    public static final List<IRCCommand> ChanOwnOpCommands = new ArrayList<>();
    public static final List<IRCCommand> ChanHalfOpCommands = new ArrayList<>();
    public static final List<AuthedUser> AuthedUsers = new ArrayList<>();
    public static final List<FunObject> Attacks = new ArrayList<>();
    public static final List<String> Eightball = new ArrayList<>();
    public static final List<IRCCommand> ChanOpCommands = new ArrayList<>();
    public static final List<IRCCommand> ChanAdminCommands = new ArrayList<>();
    public static final MultiBotManager WaveTact = new MultiBotManager();
    public static final List<ConsoleCommand> ConsoleCommands = new ArrayList<>();
    public static final List<String> ConsoleListCommands = new ArrayList<>();
    public static final List<String> GenericListCommands = new ArrayList<>();
    public static final List<String> TrustedListCommands = new ArrayList<>();
    public static final List<String> ControllerListCommands = new ArrayList<>();
    public static final List<String> AllListCommands = new ArrayList<>();
    public static final List<String> ChanOwnOpListCommands = new ArrayList<>();
    public static final List<String> NetAdminListCommands = new ArrayList<>();
    public static final List<String> ChanHalfOpListCommands = new ArrayList<>();
    public static final List<String> ChanOpListCommands = new ArrayList<>();
    public static final List<String> ChanAdminListCommands = new ArrayList<>();
    public static final StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
    public static final Reflections wavetactreflection = new Reflections("com.techcavern.wavetact");
    public static final List<NetProperty> NetworkName = new ArrayList<>();
    public static String wundergroundapikey = null;
    public static String wolframalphaapikey = null;
    public static String wordnikapikey = null;
    public static String googleapikey = null;
    public static ConsoleServer consoleServer = new ConsoleServer();
    public static DSLContext WaveTactDB = null;
}
