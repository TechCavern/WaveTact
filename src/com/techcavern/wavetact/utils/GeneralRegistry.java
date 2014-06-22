package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.objects.SimpleAction;
import com.techcavern.wavetact.objects.SimpleMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralRegistry {

    public static String CommandChar = "@";
    public static List<String> Controllers = Arrays.asList(new String[]{"jztech101", "Archtikz", "kaendfinger"});
    public static List<String> ControllerHostmasks = Arrays.asList(new String[]{"*!jztech101@crabhost.org"});
    public static String OvdServer = "irc.overdrive.pw";
    public static List<String> OvdChannels = Arrays.asList(new String[]{"#techcavern", "#dev"});
    public static String OvdNick = "WaveTact";
    public static String EsperServer = "irc.esper.net";
    public static String EsperNick = "Wavetact";
    public static List<String> EsperChannels = Arrays.asList(new String[]{"#techcavern"});
    public static String ECodeServer = "irc.electrocode.net";
    public static List<String> ECodeChannels = Arrays.asList(new String[]{"#techcavern", "#bots"});
    public static String ECodeNick = "WaveTact";
    public static String XertionServer = "irc.xertion.org";
    public static List<String> XertionChannels = Arrays.asList(new String[]{"#techcavern"});
    public static String XertionNick = "WaveTact";
    public static String ObsidianServer = "irc.obsidianirc.net";
    public static List<String> ObsidianChannels = Arrays.asList(new String[]{"#techcavern", "#szsocial"});
    public static String ObsidianNick = "WaveTact";
    public static List<Command> Commands = new ArrayList();
    public static List<SimpleMessage> SimpleMessages = new ArrayList();
    public static List<SimpleAction> SimpleActions = new ArrayList();
    public static List<String> HighFives = new ArrayList();
    

   

}
