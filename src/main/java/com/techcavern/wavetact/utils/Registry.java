package com.techcavern.wavetact.utils;

import com.google.common.collect.HashBiMap;
import com.techcavern.wavetact.console.ConsoleServer;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.IRCCommand;
import org.apache.commons.validator.routines.UrlValidator;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jooq.DSLContext;
import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.WhoisEvent;
import org.reflections.Reflections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

public class Registry {
    public static final ForkJoinPool threadPool = new ForkJoinPool(20);
    public static final Map<String, IRCCommand> ircCommands = new HashMap<>();
    public static final List<IRCCommand> ircCommandList = new ArrayList<>();
    public static final List<ConsoleCommand> consoleCommandList = new ArrayList<>();
    public static final Map<PircBotX, Map<String, String>> authedUsers = new ConcurrentHashMap<>();
    public static final List<String> attacks = new ArrayList<>();
    public static final List<String> eightBall = new ArrayList<>();
    public static final Map<String, String> charReplacements = new HashMap<>();
    public static final MultiBotManager WaveTact = new MultiBotManager();
    public static final Map<String, ConsoleCommand> consoleCommands = new HashMap<>();
    public static final StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
    public static final Reflections wavetactReflection = new Reflections("com.techcavern.wavetact");
    public static final HashBiMap<String, PircBotX> networks = HashBiMap.create();
    public static final Map<PircBotX, Map<String, WhoisEvent>> whoisEventCache = new ConcurrentHashMap<>();
    public static final int CURRENT_ITERATION = 9;
    public static final ConsoleServer consoleServer = new ConsoleServer();
    public static final Map<PircBotX, Queue<String>> messageQueue = new HashMap<>();
    public static final UrlValidator urlValidator = new UrlValidator();
    public static final String VERSION = "WaveTact 1.1-dev";
    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36";
    public static Map<PircBotX, String> lastLeftChannel = new HashMap<>();
    public static DSLContext wavetactDB = null;
    public static Map<PircBotX, String> lastWhois = new HashMap<>();
    public static Map<PircBotX, Boolean> hasConnected = new HashMap();
}
