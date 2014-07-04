package com.techcavern.wavetact.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.commands.chanop.*;
import com.techcavern.wavetact.commands.controller.IRCRaw;
import com.techcavern.wavetact.commands.controller.Join;
import com.techcavern.wavetact.commands.controller.Lock;
import com.techcavern.wavetact.commands.controller.Shutdown;
import com.techcavern.wavetact.commands.fun.SomethingAwesome;
import com.techcavern.wavetact.commands.fun.UrbanDictonary;
import com.techcavern.wavetact.commands.trusted.*;
import com.techcavern.wavetact.commands.utils.*;
import com.techcavern.wavetact.utils.events.DisconnectListener;
import com.techcavern.wavetact.utils.events.KickListener;
import com.techcavern.wavetact.utils.events.MessageListener;
import com.techcavern.wavetact.utils.objects.*;
import com.techcavern.wavetact.utils.thread.CheckTime;
import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

//import com.techcavern.wavetact.Commands.Commands.TestCommand;

public class LoadUtils {

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static PircBotX createbot(String nickservPassword, String name, List<String> channels, String nick, String server) {
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
        if (nickservPassword != null) {
            Net.setNickservPassword(nickservPassword);
        }
        return new PircBotX(Net.buildConfiguration());
    }

    public static void registerNetworks() {
        File serversFolder = new File("servers/");
        serversFolder.mkdir();
        File[] files = serversFolder.listFiles();
        String name;
        com.techcavern.wavetact.utils.objects.Configuration config;
        for (File f : files) {
            if (!f.isDirectory()) {
                name = f.getName();
                name = name.substring(0, f.getName().lastIndexOf('.'));
                config = new com.techcavern.wavetact.utils.objects.Configuration(f);
                GeneralRegistry.configs.put(name, config);
            }
        }

        PircBotX bot;
        LinkedList<String> chans = new LinkedList<String>();
        String nsPass;
        for (com.techcavern.wavetact.utils.objects.Configuration c : GeneralRegistry.configs.values()) {
            Collections.addAll(chans, c.getString("channels").split(", "));
            if (c.getString("nickserv").equalsIgnoreCase("False")) {
                nsPass = null;
            } else {
                nsPass = c.getString("nickserv");
            }

            bot = LoadUtils.createbot(nsPass, c.getString("name"), chans, c.getString("nick"), c.getString("server"));
            GeneralRegistry.WaveTact.addBot(bot);
            new CommandChar(c.getString("prefix"), bot);
        }
    }

    public static void registerDevServer() {
        List<String> Chans = Arrays.asList("#techcavern");
        PircBotX Dev = LoadUtils.createbot(null, "EsperNet", Chans, "WaveTactDev", "irc.esper.net");
        GeneralRegistry.WaveTact.addBot(Dev);
        new CommandChar("@", Dev);
    }


    public static void startThreads() {
        (new Thread(new CheckTime())).start();
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

    public static void registerCommands() {
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
