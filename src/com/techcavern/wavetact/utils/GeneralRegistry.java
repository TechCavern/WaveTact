package com.techcavern.wavetact.utils;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GeneralRegistry {

    public static String CommandChar = "@";
    public static List<String> Controllers = Arrays.asList("jztech101", "Archtikz");
    public static List<String> ControllerHostmasks = Arrays.asList("*!jztech101@crabhost.org");
    public static String OvdServer = "irc.overdrive.pw";
    public static List<String> OvdChannels = Arrays.asList("#techcavern", "#dev");
    public static String OvdNick = "WaveTact";
    public static String EsperServer = "irc.esper.net";
    public static String EsperNick = "Wavetact";
    public static List<String> EsperChannels = Arrays.asList("#techcavern");
    public static String ECodeServer = "irc.electrocode.net";
    public static List<String> ECodeChannels = Arrays.asList("#techcavern", "#bots");
    public static String ECodeNick = "WaveTact";
    public static String XertionServer = "irc.xertion.org";
    public static List<String> XertionChannels = Arrays.asList("#techcavern");
    public static String XertionNick = "WaveTact";
    public static List<Command> Commands = new ArrayList<Command>();
    public static List<String> HighFives = new ArrayList<String>();
    public static Set<SimpleMessage> SimpleMessage = Sets.newConcurrentHashSet();
    public static Set<SimpleAction> SimpleAction = Sets.newConcurrentHashSet();

}
