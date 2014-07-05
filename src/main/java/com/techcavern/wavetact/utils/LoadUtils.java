package com.techcavern.wavetact.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techcavern.wavetact.commandline.perms.PermLevelC;
import com.techcavern.wavetact.commandline.utils.AddServer;
import com.techcavern.wavetact.commandline.utils.BasicCommands;
import com.techcavern.wavetact.commandline.utils.Start;
import com.techcavern.wavetact.commands.chanhalfop.*;
import com.techcavern.wavetact.commands.chanop.*;
import com.techcavern.wavetact.commands.chanowner.AutoOp;
import com.techcavern.wavetact.commands.chanowner.CPermLevel;
import com.techcavern.wavetact.commands.chanowner.Owner;
import com.techcavern.wavetact.commands.chanowner.Protect;
import com.techcavern.wavetact.commands.controller.*;
import com.techcavern.wavetact.commands.fun.Attack;
import com.techcavern.wavetact.commands.fun.SomethingAwesome;
import com.techcavern.wavetact.commands.fun.UrbanDictonary;
import com.techcavern.wavetact.commands.trusted.*;
import com.techcavern.wavetact.commands.utils.*;
import com.techcavern.wavetact.commands.utils.Help;
import com.techcavern.wavetact.utils.events.DisconnectListener;
import com.techcavern.wavetact.utils.events.JoinListener;
import com.techcavern.wavetact.utils.events.KickListener;
import com.techcavern.wavetact.utils.events.MessageListener;
import com.techcavern.wavetact.utils.objects.*;
import com.techcavern.wavetact.utils.thread.CheckTime;
import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;

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
        Net.setRealName(nick);
        Net.getListenerManager().addListener(new MessageListener());
        Net.getListenerManager().addListener(new DisconnectListener());
        Net.getListenerManager().addListener(new JoinListener());


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
        com.techcavern.wavetact.utils.fileUtils.Configuration config;
        for (File f : files) {
            if (!f.isDirectory()) {
                name = f.getName();
                name = name.substring(0, f.getName().lastIndexOf('.'));
                config = new com.techcavern.wavetact.utils.fileUtils.Configuration(f);
                GeneralRegistry.configs.put(name, config);
            }
        }

        PircBotX bot;
        LinkedList<String> chans = new LinkedList<String>();
        String nsPass;
        for (com.techcavern.wavetact.utils.fileUtils.Configuration c : GeneralRegistry.configs.values()) {
            chans.clear();
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
        GeneralRegistry.Controllers.add("JZTech101");
        new CommandChar("@", Dev);
    }


    public static void startThreads() {
        (new Thread(new CheckTime())).start();
    }

    public static void registerCommands() {
        /**
         try{
         GeneralRegistry.COMMANDS.addAll(GeneralRegistry.TASKS.submit(new CommandCollection("com.techcavern.wavetact.commands")).get());
         } catch(Exception ex){
         ex.printStackTrace(System.err);
         }
         **/
        GeneralRegistry.Commands.add(new Ban());
        GeneralRegistry.Commands.add(new HalfOp());
        GeneralRegistry.Commands.add(new Kick());
        GeneralRegistry.Commands.add(new Mode());
        GeneralRegistry.Commands.add(new Owner());
        GeneralRegistry.Commands.add(new Protect());
        GeneralRegistry.Commands.add(new Quiet());
        GeneralRegistry.Commands.add(new Voice());
        GeneralRegistry.Commands.add(new Op());
        GeneralRegistry.Commands.add(new IRCRaw());
        GeneralRegistry.Commands.add(new Join());
        GeneralRegistry.Commands.add(new Lock());
        GeneralRegistry.Commands.add(new Shutdown());
        GeneralRegistry.Commands.add(new SomethingAwesome());
        GeneralRegistry.Commands.add(new UrbanDictonary());
        GeneralRegistry.Commands.add(new Act());
        GeneralRegistry.Commands.add(new CustomCMD());
        GeneralRegistry.Commands.add(new Part());
        GeneralRegistry.Commands.add(new Say());
        GeneralRegistry.Commands.add(new WolframAlpha());
        GeneralRegistry.Commands.add(new CheckUserLevel());
        GeneralRegistry.Commands.add(new Commands());
        GeneralRegistry.Commands.add(new Define());
        GeneralRegistry.Commands.add(new FindIP());
        GeneralRegistry.Commands.add(new Help());
        GeneralRegistry.Commands.add(new Hostmask());
        GeneralRegistry.Commands.add(new MathC());
        GeneralRegistry.Commands.add(new PingTime());
        GeneralRegistry.Commands.add(new Question());
        GeneralRegistry.Commands.add(new Weather());
        GeneralRegistry.Commands.add(new Topic());
        GeneralRegistry.Commands.add(new Attack());
        GeneralRegistry.Commands.add(new DefCon());
        GeneralRegistry.Commands.add(new CPermLevel());
        GeneralRegistry.Commands.add(new AutoOp());



    }

    public static void initializeCommandlines() {
        //TODO Fix this to work with @CMDLine once @CMD is fixed
        GeneralRegistry.CommandLines.add(new AddServer());
        GeneralRegistry.CommandLines.add(new com.techcavern.wavetact.commandline.Help());
        GeneralRegistry.CommandLines.add(new BasicCommands());
        GeneralRegistry.CommandLines.add(new Start());
        GeneralRegistry.CommandLines.add(new PermLevelC());

    }

    public static void parseCommandLineArguments(String[] args) {
        boolean exit = false;
        boolean invalid = true;

        if (args.length < 1) {
            com.techcavern.wavetact.commandline.Help c = new com.techcavern.wavetact.commandline.Help();
            c.doAction(args);
        }

        for (CommandLine c : GeneralRegistry.CommandLineArguments) {
            for (String b : c.getArgument()) {
                for (String s : args) {

                    if (s.equalsIgnoreCase("-" + b)) {
                        c.doAction(args);
                        invalid = false;


                    }
                }
            }
        }
        for (CommandLine c : GeneralRegistry.CommandLines) {
            for (String b : c.getArgument()) {

                if (args[0].equalsIgnoreCase("-" + b)) {
                    c.doAction(args);
                    invalid = false;


                }
            }
        }
        if (invalid) System.exit(0);
    }
}
