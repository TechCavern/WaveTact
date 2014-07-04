package com.techcavern.wavetact.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.events.DisconnectListener;
import com.techcavern.wavetact.events.KickListener;
import com.techcavern.wavetact.events.MessageListener;
import com.techcavern.wavetact.objects.*;
import com.techcavern.wavetact.thread.CheckTime;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.output.OutputChannel;
import org.pircbotx.output.OutputUser;
import com.techcavern.wavetact.commands.chanop.*;
import com.techcavern.wavetact.commands.controller.*;
import com.techcavern.wavetact.commands.fun.*;
import com.techcavern.wavetact.commands.trusted.*;
import com.techcavern.wavetact.commands.utils.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

//import com.techcavern.wavetact.Commands.Commands.TestCommand;

public class IRCUtils {

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static PircBotX createbot(String g, String name, List<String> channels, String nick, String server) {
        System.out.println("Configuring " + name);
        Builder<PircBotX> Net = new Configuration.Builder<PircBotX>();
        Net.setName(nick);
        Net.setLogin("WaveTact");
        Net.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
        // TODO: @logangorence Add support for port changes. Also, does PircBotX support SSL? I think so
        Net.setServer(server, 6667);
        channels.forEach(Net::addAutoJoinChannel);
        Net.getListenerManager().addListener(new MessageListener());
        Net.getListenerManager().addListener(new DisconnectListener());

        //    TODO: @logangorence Add support for saving configuration to allow for configuration per-network on "modules"... Should we also modularize? Anyways, each network will be able to enable/disable modules and if they are enabled, it will be added to the server file(maybe later on, server folders, which would have a server.info, modules.info, and possibly logs and other stuff).
        //    Hm... the only module I can currently think of is "HighFive"

        //    Net.getListenerManager().addListener(new HighFive());
        Net.getListenerManager().addListener(new KickListener());
        if (g != null) {
            Net.setNickservPassword(g);
        }
        return new PircBotX(Net.buildConfiguration());
    }

    public static void registerNetworks(){
        File serversFolder = new File("servers/");
        serversFolder.mkdir();
        File[] files = serversFolder.listFiles();
        String name;
        com.techcavern.wavetact.utils.Configuration config;
        for (File f : files)
        {
            if (!f.isDirectory())
            {
                name = f.getName();
                name = name.substring(0, f.getName().lastIndexOf('.'));
                config = new com.techcavern.wavetact.utils.Configuration(f);
                GeneralRegistry.configs.put(name, config);
            }
        }

        PircBotX bot;
        LinkedList<String> chans = new LinkedList<String>();
        String ns;
        for (com.techcavern.wavetact.utils.Configuration c : GeneralRegistry.configs.values())
        {
            Collections.addAll(chans, c.getString("channels").split(", "));
            if(c.getString("nickserv").equalsIgnoreCase("False")){
                ns = null;
            }else{
                ns = c.getString("nickserv");
            }

            bot = IRCUtils.createbot(ns, c.getString("name"), chans,c.getString("nick"), c.getString("server"));
            GeneralRegistry.WaveTact.addBot(bot);
            new CommandChar(c.getString("prefix"), bot);
        }
    }

    public static void registerDevServer(){
        List<String> Chans = Arrays.asList("#techcavern");
        PircBotX Dev = IRCUtils.createbot(null, "EsperNet", Chans, "WaveTactDev", "irc.esper.net");
        GeneralRegistry.WaveTact.addBot(Dev);
        new CommandChar("@", Dev);
    }

    public static User getUserByNick(Channel c, String n) {
        for (User u : c.getUsers()) {
            if (u.getNick().equalsIgnoreCase(n)) {
                return u;
            }
        }
        return null;
    }

    public static Channel getChannelbyName(PircBotX b, String n) {
        for (Channel u : b.getUserBot().getChannels()) {
            if (u.getName().equalsIgnoreCase(n)) {
                return u;
            }
        }
        return null;
    }

    public static PircBotX getBotByNetwork(String n) {
        for (PircBotX c : GeneralRegistry.WaveTact.getBots()) {
            if (c.getServerInfo().getNetwork().equals(n)) {
                return c;
            }
        }
        return null;
    }

    public static void setMode(Channel c, PircBotX b, String d, String u) {
        OutputChannel o = new OutputChannel(b, c);
        if (u != null) {
            o.setMode(d, u);
        } else {
            o.setMode(d);
        }
    }

    public static void SendNotice(PircBotX b, User u, String s) {
        OutputUser x = new OutputUser(b, u);
        x.notice(s);
    }

    public static void startThreads(){
        (new Thread(new CheckTime())).start();
    }

    public static Command getCommand(String Command) {
        for (Command g : GeneralRegistry.Commands) {
            if (g.getCommand().equalsIgnoreCase(Command)) {
                return g;
            }
        }
        return null;

    }

    public static SimpleMessage getSimpleMessage(String SimpleAction) {
        for (SimpleMessage g : GeneralRegistry.SimpleMessages) {
            if (g.getCommand().equalsIgnoreCase(SimpleAction)) {
                return g;
            }
        }
        return null;

    }

    public static SimpleAction getSimpleAction(String SimpleAction) {
        for (SimpleAction g : GeneralRegistry.SimpleActions) {
            if (g.getCommand().equalsIgnoreCase(SimpleAction)) {
                return g;
            }
        }
        return null;
    }

    public static void saveSimpleActions() {
        JSONFile file = new JSONFile("SimpleActions.json");
        try {
            file.write(GeneralRegistry.SimpleActions);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadSimpleActions() {
        JSONFile file = new JSONFile("SimpleActions.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> actions = file.read(List.class);
                GeneralRegistry.SimpleActions.clear();
                GeneralRegistry.SimpleActions.addAll(actions.stream().map(act -> new SimpleAction(
                        ((ArrayList<String>) act.get("comid")).get(0),
                        ((Double) act.get("PermLevel")).intValue(),
                        (String) act.get("action"),
                        (Boolean) act.get("locked"))).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveSimpleMessages() {
        JSONFile file = new JSONFile("SimpleMessages.json");
        try {
            file.write(GeneralRegistry.SimpleMessages);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }
    
    public static void registerCommands(){
      /**
        try{
            GeneralRegistry.COMMANDS.addAll(GeneralRegistry.TASKS.submit(new CommandCollection("com.techcavern.wavetact.commands")).get());
        } catch(Exception ex){
            ex.printStackTrace(System.err);
        }
       **/
        new Ban();
        new HalfOp();
        new Kick();
        new Mode();
        new Owner();
        new Protect();
        new Quiet();
        new Voice();
        new IRCRaw();
        new Join();
        new Lock();
        new Shutdown();
        new SomethingAwesome();
        new UrbanDictonary();
        new Act();
        new CustomCMD();
        new Part();
        new Say();
        new WolframAlpha();
        new CheckUserLevel();
        new Commands();
        new Define();
        new FindIP();
        new Help();
        new Hostmask();
        new MathC();
        new PingTime();
        new Question();
        new Weather();

    }

    public static String getCommandChar(PircBotX b){
        for(CommandChar d:GeneralRegistry.CommandChars){
            if(d.getBot() == b){
                return d.getCommandChar();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static void loadSimpleMessages() {
        JSONFile file = new JSONFile("SimpleMessages.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> messages = file.read();
                GeneralRegistry.SimpleMessages.clear();

                GeneralRegistry.SimpleMessages.addAll(messages.stream().map(msg -> new SimpleMessage(
                        ((ArrayList<String>) msg.get("comid")).get(0),
                        ((Double) msg.get("PermLevel")).intValue(),
                        (String) msg.get("message"),
                        (Boolean) msg.get("locked"))).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }
    
    public static UTime getBanTime(String u) {
        for (UTime x : GeneralRegistry.BanTimes) {
            if (x.getHostmask().equals(u)) {
                return x;
            }
        }
        return null;
    }

    public static void saveBanTimes() {
        JSONFile file = new JSONFile("BanTimes.json");
        try {
            file.write(GeneralRegistry.BanTimes);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadBanTimes() {
        JSONFile file = new JSONFile("BanTimes.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> actions = file.read(List.class);
                GeneralRegistry.BanTimes.clear();
                GeneralRegistry.BanTimes.addAll(actions.stream().map(act -> new UTime((String) act.get("hostmask"),
                        (String) act.get("network"),
                        (String) act.get("type"),
                        (String) act.get("channel"),
                        ((Double) act.get("time")).longValue())).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }
    
    public static UTime getQuietTime(String u) {
        for (UTime x : GeneralRegistry.QuietTimes) {
            if (x.getHostmask().equals(u)) {
                return x;
            }
        }
        return null;
    }

    public static void saveQuietTimes() {
        JSONFile file = new JSONFile("BanTimes.json");
        try {
            file.write(GeneralRegistry.QuietTimes);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadQuietTimes() {
        JSONFile file = new JSONFile("QuietTimes.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> actions = file.read(List.class);
                GeneralRegistry.QuietTimes.clear();
                GeneralRegistry.QuietTimes.addAll(actions.stream().map(act -> new UTime((String) act.get("hostmask"),
                        (String) act.get("network"),
                        (String) act.get("type"),
                        (String) act.get("channel"),
                        ((Double) act.get("time")).longValue())).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }
}
