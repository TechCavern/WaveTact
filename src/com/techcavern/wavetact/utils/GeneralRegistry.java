package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.objects.SimpleAction;
import com.techcavern.wavetact.objects.SimpleMessage;
import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralRegistry {

    public static String CommandChar = "@";
    public static List<String> Controllers = Arrays.asList("jztech101", "Archtikz", "kaendfinger", "deathcrazyuberlironman");
    public static List<String> ControllerHostmasks = Arrays.asList("*!jztech101@crabhost.org");
    public static String OvdServer = "irc.overdrive.pw";
    public static List<String> OvdChannels = Arrays.asList("#techcavern", "#dev");
    public static String OvdNick = "WaveTact";
    public static String EsperServer = "irc.esper.net";
    public static String EsperNick = "Wavetact";
    public static List<String> EsperChannels = Arrays.asList("#directcode");
    public static String ECodeServer = "irc.electrocode.net";
    public static List<String> ECodeChannels = Arrays.asList("#techcavern", "#bots");
    public static String ECodeNick = "WaveTact";
    public static String XertionServer = "irc.xertion.org";
    public static List<String> XertionChannels = Arrays.asList("#techcavern");
    public static String XertionNick = "WaveTact";
    public static String ObsidianServer = "irc.obsidianirc.net";
    public static List<String> ObsidianChannels = Arrays.asList("#techcavern", "#szsocial");
    public static String ObsidianNick = "WaveTact";
    public static List<Command> Commands = new ArrayList<>();
    public static List<SimpleMessage> SimpleMessages = new ArrayList<>();
    public static List<SimpleAction> SimpleActions = new ArrayList<>();
    public static List<String> HighFives = new ArrayList<>();
    public static MultiBotManager<PircBotX> WaveTact = new MultiBotManager<PircBotX>();
}
