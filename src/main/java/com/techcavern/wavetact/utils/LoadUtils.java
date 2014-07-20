package com.techcavern.wavetact.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techcavern.wavetact.commandline.perms.PermLevelC;
import com.techcavern.wavetact.commandline.utils.AddServer;
import com.techcavern.wavetact.commandline.utils.BasicCommands;
import com.techcavern.wavetact.commandline.utils.Start;
import com.techcavern.wavetact.commands.Anonymonity.Act;
import com.techcavern.wavetact.commands.Anonymonity.Say;
import com.techcavern.wavetact.commands.chanhalfop.*;
import com.techcavern.wavetact.commands.chanop.*;
import com.techcavern.wavetact.commands.chanop.AutoOp;
import com.techcavern.wavetact.commands.chanfounder.CPermLevel;
import com.techcavern.wavetact.commands.chanowner.Owner;
import com.techcavern.wavetact.commands.chanowner.Protect;
import com.techcavern.wavetact.commands.controller.*;
import com.techcavern.wavetact.commands.fun.Attack;
import com.techcavern.wavetact.commands.fun.SomethingAwesome;
import com.techcavern.wavetact.commands.fun.UrbanDictonary;
import com.techcavern.wavetact.commands.global.Disconnect;
import com.techcavern.wavetact.commands.global.Join;
import com.techcavern.wavetact.commands.trusted.*;
import com.techcavern.wavetact.commands.utils.*;
import com.techcavern.wavetact.commands.utils.Help;
import com.techcavern.wavetact.utils.events.*;
import com.techcavern.wavetact.utils.objects.*;
import com.techcavern.wavetact.utils.thread.CheckTime;
import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;

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
        Net.setServer(server, 6667);
        channels.forEach(Net::addAutoJoinChannel);
        Net.setRealName(nick);
        Net.getListenerManager().addListener(new MessageListener());
        Net.getListenerManager().addListener(new JoinListener());
        Net.getListenerManager().addListener(new CTCPListener());
        Net.getListenerManager().addListener(new KickListener());
        Net.getListenerManager().addListener(new PrivateMessageListener());
        Net.setAutoReconnect(true);
        //    Net.getListenerManager().addListener(new HighFive());
        if (nickservPassword != null) {
            Net.setNickservPassword(nickservPassword);
        }
        return new PircBotX(Net.buildConfiguration());
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
        GeneralRegistry.ChanHalfOpCommands.add(new Ban());
        GeneralRegistry.ChanOpCommands.add(new HalfOp());
        GeneralRegistry.ChanHalfOpCommands.add(new Kick());
        GeneralRegistry.ChanOpCommands.add(new Mode());
        GeneralRegistry.ChanOwnerCommands.add(new Owner());
        GeneralRegistry.ChanOwnerCommands.add(new Protect());
        GeneralRegistry.ChanHalfOpCommands.add(new Quiet());
        GeneralRegistry.ChanHalfOpCommands.add(new Voice());
        GeneralRegistry.ChanOpCommands.add(new Op());
        GeneralRegistry.ControllerCommands.add(new IRCRaw());
        GeneralRegistry.ControllerCommands.add(new Join());
        GeneralRegistry.ControllerCommands.add(new Lock());
        GeneralRegistry.ControllerCommands.add(new Shutdown());
        GeneralRegistry.GenericCommands.add(new SomethingAwesome());
        GeneralRegistry.GenericCommands.add(new UrbanDictonary());
        GeneralRegistry.TrustedCommands.add(new Act());
        GeneralRegistry.TrustedCommands.add(new CustomCMD());
        GeneralRegistry.ChanHalfOpCommands.add(new Part());
        GeneralRegistry.TrustedCommands.add(new Say());
        GeneralRegistry.TrustedCommands.add(new WolframAlpha());
        GeneralRegistry.GenericCommands.add(new CheckUserLevel());
        GeneralRegistry.GenericCommands.add(new Commands());
        GeneralRegistry.GenericCommands.add(new Define());
        GeneralRegistry.GenericCommands.add(new FindIP());
        GeneralRegistry.GenericCommands.add(new Help());
        GeneralRegistry.GenericCommands.add(new Hostmask());
        GeneralRegistry.GenericCommands.add(new MathC());
        GeneralRegistry.GenericCommands.add(new PingTime());
        GeneralRegistry.GenericCommands.add(new Question());
        GeneralRegistry.GenericCommands.add(new Weather());
        GeneralRegistry.ChanHalfOpCommands.add(new Topic());
        GeneralRegistry.GenericCommands.add(new Attack());
        GeneralRegistry.ControllerCommands.add(new DefCon());
        GeneralRegistry.ChanFounderCommands.add(new CPermLevel());
        GeneralRegistry.ChanOpCommands.add(new AutoOp());
        GeneralRegistry.ControllerCommands.add(new Globals());
        GeneralRegistry.GlobalCommands.add(new Disconnect());
        GeneralRegistry.GenericCommands.add(new MCStatus());


    }

    public static void initializeCommandlines() {
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
    public static void registerAttacks(){

        GeneralRegistry.Attacks.add(new FunObject("sends a 53 inch monitor flying at $*" , false, null));
        GeneralRegistry.Attacks.add(new FunObject("shoots a rocket at $*" , false, null));
        GeneralRegistry.Attacks.add(new FunObject("punches $* right in the crotch", false, null));
        GeneralRegistry.Attacks.add(new FunObject("places a bomb near $* set for 2 seconds", true, "BANG!"));
        GeneralRegistry.Attacks.add(new FunObject("drops a 2000 pound object on $*" , false, null));
        GeneralRegistry.Attacks.add(new FunObject("packs $* up and ships it off to another galaxy", false, null));
        GeneralRegistry.Attacks.add(new FunObject("eats $* up for breakfast", false, null));
        GeneralRegistry.Attacks.add(new FunObject("sends a flying desk at $*", false, null));
        GeneralRegistry.Attacks.add(new FunObject("swallows $* whole", false, null));
        GeneralRegistry.Attacks.add(new FunObject("runs over $*", true, "HEY! WATCH WHERE YOU'RE GOING!"));
        GeneralRegistry.Attacks.add(new FunObject("throws a racket at $*", false, null));
    }
    public static void registerCommandList(){
        for(GenericCommand command : GeneralRegistry.GenericCommands){
            GeneralRegistry.GenericListCommands.add(command.getCommand());
            GeneralRegistry.TrustedListCommands.add(command.getCommand());
            GeneralRegistry.ChanHalfOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());

        }
        for(GenericCommand command : GeneralRegistry.TrustedCommands){
            GeneralRegistry.TrustedListCommands.add(command.getCommand());
            GeneralRegistry.ChanHalfOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());
        }
        for(GenericCommand command : GeneralRegistry.AnonymonityCommands){
            GeneralRegistry.TrustedListCommands.add(command.getCommand());
            GeneralRegistry.ChanHalfOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());

        }
        for(GenericCommand command : GeneralRegistry.ChanHalfOpCommands){
            GeneralRegistry.ChanHalfOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());

        }
        for(GenericCommand command : GeneralRegistry.ChanOpCommands){
            GeneralRegistry.ChanOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());

        }
        for(GenericCommand command : GeneralRegistry.ChanOwnerCommands){
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());
        }
        for(GenericCommand command : GeneralRegistry.ChanFounderCommands){
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());
        }
        for(GenericCommand command : GeneralRegistry.GlobalCommands){
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());
        }
        for(GenericCommand command : GeneralRegistry.ControllerCommands){
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
        }
    }
}
