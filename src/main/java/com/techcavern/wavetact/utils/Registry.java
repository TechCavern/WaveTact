package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.console.ConsoleServer;
import com.techcavern.wavetact.objects.*;
import org.apache.commons.validator.routines.UrlValidator;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jooq.DSLContext;
import org.pircbotx.MultiBotManager;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Registry {
    public static String LastLeftChannel = "";
    public static String LastWhois = "";
    public static final ForkJoinPool threadPool = new ForkJoinPool(20);
    public static final List<IRCCommand> IRCCommands = new ArrayList<>();
    public static final List<AuthedUser> AuthedUsers = new ArrayList<>();
    public static final List<String> Attacks = new ArrayList<>();
    public static final List<String> Eightball = new ArrayList<>();
    public static final MultiBotManager WaveTact = new MultiBotManager();
    public static final List<ConsoleCommand> ConsoleCommands = new ArrayList<>();
    public static final StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
    public static final Reflections wavetactreflection = new Reflections("com.techcavern.wavetact");
    public static final List<NetProperty> NetworkName = new ArrayList<>();
    public static List<CachedWhoisEvent> WhoisEventCache = new ArrayList<>();
    public static String wundergroundapikey = null;
    public static String wolframalphaapikey = null;
    public static String wordnikapikey = null;
    public static String googleapikey = null;
    public static ConsoleServer consoleServer = new ConsoleServer();
    public static DSLContext WaveTactDB = null;
    public static final List<NetProperty> MessageQueue = new ArrayList<>();
    public static final UrlValidator urlvalidator = new UrlValidator();
    public static final String Version = "WaveTact 0.8.2-dev";
}
