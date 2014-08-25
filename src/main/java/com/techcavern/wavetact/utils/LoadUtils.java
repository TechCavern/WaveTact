package com.techcavern.wavetact.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techcavern.wavetact.annot.AnonCMD;
import com.techcavern.wavetact.commandline.perms.PermLevelC;
import com.techcavern.wavetact.commandline.utils.AddServer;
import com.techcavern.wavetact.commandline.utils.BasicCommands;
import com.techcavern.wavetact.commandline.utils.Config;
import com.techcavern.wavetact.commandline.utils.Start;
import com.techcavern.wavetact.commands.anonymonity.Act;
import com.techcavern.wavetact.commands.anonymonity.Say;
import com.techcavern.wavetact.commands.auth.*;
import com.techcavern.wavetact.commands.chanfounder.CPermLevel;
import com.techcavern.wavetact.commands.chanhalfop.*;
import com.techcavern.wavetact.commands.chanop.AutoOp;
import com.techcavern.wavetact.commands.chanop.HalfOp;
import com.techcavern.wavetact.commands.chanop.Notice;
import com.techcavern.wavetact.commands.chanop.Op;
import com.techcavern.wavetact.commands.chanowner.Owner;
import com.techcavern.wavetact.commands.chanowner.Protect;
import com.techcavern.wavetact.commands.controller.*;
import com.techcavern.wavetact.commands.controller.Shutdown;
import com.techcavern.wavetact.commands.fun.Attack;
import com.techcavern.wavetact.commands.fun.FMyLife;
import com.techcavern.wavetact.commands.fun.SomethingAwesome;
import com.techcavern.wavetact.commands.fun.UrbanDictonary;
import com.techcavern.wavetact.commands.global.DNSBLModify;
import com.techcavern.wavetact.commands.global.Disconnect;
import com.techcavern.wavetact.commands.global.IRCBLModify;
import com.techcavern.wavetact.commands.global.Join;
import com.techcavern.wavetact.commands.trusted.*;
import com.techcavern.wavetact.commands.utils.*;
import com.techcavern.wavetact.utils.eventListeners.*;
import com.techcavern.wavetact.utils.objects.CommandLine;
import com.techcavern.wavetact.utils.objects.FunObject;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import io.github.asyncronous.mirrors.*;
import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;
import io.github.asyncronous.mirrors;

import java.nio.charset.Charset;
import java.util.List;

public class LoadUtils {

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static PircBotX createbot(String nickservPassword, String name, List<String> channels, String nick, String server) {
        System.out.println("Configuring " + name);
        Builder<PircBotX> Net = new Configuration.Builder<>();
        Net.setName(nick);
        Net.setLogin("WaveTact");
        Net.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
        Net.setServer(server, 6667);
        channels.forEach(Net::addAutoJoinChannel);
        Net.setRealName(nick);
        Net.getListenerManager().addListener(new ChanMsgListener());
        Net.getListenerManager().addListener(new JoinListener());
        Net.getListenerManager().addListener(new CTCPListener());
        Net.getListenerManager().addListener(new KickListener());
        Net.getListenerManager().addListener(new PrivMsgListener());
        Net.setAutoReconnect(true);
        if (nickservPassword != null) {
            Net.setNickservPassword(nickservPassword);
        }
        return new PircBotX(Net.buildConfiguration());
    }

    public static void registerCommands() {
        GeneralRegistry.ChanHalfOpCommands.add(new Ban());
        GeneralRegistry.ChanOpCommands.add(new HalfOp());
        GeneralRegistry.ChanHalfOpCommands.add(new Kick());
        GeneralRegistry.ChanHalfOpCommands.add(new Mode());
        GeneralRegistry.ChanHalfOpCommands.add(new Invite());
        GeneralRegistry.ChanOwnerCommands.add(new Owner());
        GeneralRegistry.ChanOwnerCommands.add(new Protect());
        GeneralRegistry.ChanHalfOpCommands.add(new Quiet());
        GeneralRegistry.ChanHalfOpCommands.add(new Voice());
        GeneralRegistry.ChanOpCommands.add(new Op());
        GeneralRegistry.ChanOpCommands.add(new Notice());
        GeneralRegistry.ControllerCommands.add(new IRCRaw());
        GeneralRegistry.ControllerCommands.add(new Join());
        GeneralRegistry.ControllerCommands.add(new LockMSG());
        GeneralRegistry.ControllerCommands.add(new Shutdown());
        GeneralRegistry.FunCommands.add(new SomethingAwesome());
        GeneralRegistry.FunCommands.add(new UrbanDictonary());
        GeneralRegistry.TrustedCommands.add(new Act());
        GeneralRegistry.TrustedCommands.add(new CustomMSG());
        GeneralRegistry.TrustedCommands.add(new CustomACT());
        GeneralRegistry.ChanHalfOpCommands.add(new Part());
        GeneralRegistry.TrustedCommands.add(new Say());
        GeneralRegistry.GenericCommands.add(new CheckUserLevel());
        GeneralRegistry.GenericCommands.add(new Commands());
        GeneralRegistry.GenericCommands.add(new Define());
        GeneralRegistry.GenericCommands.add(new FindIP());
        GeneralRegistry.GenericCommands.add(new Help());
        GeneralRegistry.GenericCommands.add(new Hostmask());
        GeneralRegistry.GenericCommands.add(new MCAccountInfo());
        GeneralRegistry.GenericCommands.add(new PingTime());
        GeneralRegistry.GenericCommands.add(new Question());
        GeneralRegistry.GenericCommands.add(new Weather());
        GeneralRegistry.ChanHalfOpCommands.add(new Topic());
        GeneralRegistry.FunCommands.add(new Attack());
        GeneralRegistry.ControllerCommands.add(new DefCon());
        GeneralRegistry.ChanFounderCommands.add(new CPermLevel());
        GeneralRegistry.ChanOpCommands.add(new AutoOp());
        GeneralRegistry.ControllerCommands.add(new Globals());
        GeneralRegistry.GlobalCommands.add(new Disconnect());
        GeneralRegistry.GenericCommands.add(new MCStatus());
   //     GeneralRegistry.ControllerCommands.add(new TestCommand());
        GeneralRegistry.GenericCommands.add(new Google());
        GeneralRegistry.ChanHalfOpCommands.add(new Cycle());
        GeneralRegistry.GenericCommands.add(new FMyLife());
        GeneralRegistry.GenericCommands.add(new MCMods());
        GeneralRegistry.GenericCommands.add(new Calculate());
        GeneralRegistry.ControllerCommands.add(new LockACT());
        GeneralRegistry.AuthCommands.add(new Authenticate());
        GeneralRegistry.ControllerCommands.add(new ResetPass());
        GeneralRegistry.AuthCommands.add(new Register());
        GeneralRegistry.AuthCommands.add(new SetPass());
        GeneralRegistry.AuthCommands.add(new Drop());
        GeneralRegistry.AuthCommands.add(new Logout());
        GeneralRegistry.ControllerCommands.add(new FDrop());
        GeneralRegistry.TrustedCommands.add(new DNSInfo());
        GeneralRegistry.GlobalCommands.add(new IRCBLModify());
        GeneralRegistry.TrustedCommands.add(new IRCBL());
        GeneralRegistry.TrustedCommands.add(new DNSBL());
        GeneralRegistry.GlobalCommands.add(new DNSBLModify());
    }

    public static void initializeCommandlines() {
        GeneralRegistry.CommandLines.add(new AddServer());
        GeneralRegistry.CommandLines.add(new com.techcavern.wavetact.commandline.Help());
        GeneralRegistry.CommandLines.add(new BasicCommands());
        GeneralRegistry.CommandLines.add(new Start());
        GeneralRegistry.CommandLines.add(new PermLevelC());
        GeneralRegistry.CommandLines.add(new Config());
    }

    public static void parseCommandLineArguments(String[] args) {
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

    public static void registerAttacks() {
        GeneralRegistry.Attacks.add(new FunObject("sends a 53 inch monitor flying at $*", false, null));
        GeneralRegistry.Attacks.add(new FunObject("shoots a rocket at $*", false, null));
        GeneralRegistry.Attacks.add(new FunObject("punches $* right in the crotch", false, null));
        GeneralRegistry.Attacks.add(new FunObject("places a bomb near $* set for 2 seconds", true, "BANG!"));
        GeneralRegistry.Attacks.add(new FunObject("packs $* up and ships it off to another galaxy", false, null));
        GeneralRegistry.Attacks.add(new FunObject("eats $* up for breakfast", false, null));
        GeneralRegistry.Attacks.add(new FunObject("sends a flying desk at $*", false, null));
        GeneralRegistry.Attacks.add(new FunObject("swallows $* whole", false, null));
        GeneralRegistry.Attacks.add(new FunObject("ties $* up and feeds it to a shark", false, null));
        GeneralRegistry.Attacks.add(new FunObject("runs over $*", true, "HEY! WATCH WHERE YOU'RE GOING!"));
        GeneralRegistry.Attacks.add(new FunObject("throws a racket at $*", false, null));
    }

    public static void registerCommandList() {
        for (GenericCommand command : GeneralRegistry.GenericCommands) {
            GeneralRegistry.GenericListCommands.add(command.getCommand());
            GeneralRegistry.TrustedListCommands.add(command.getCommand());
            GeneralRegistry.ChanHalfOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());

        }
        for (GenericCommand command : GeneralRegistry.FunCommands) {
            GeneralRegistry.GenericListCommands.add(command.getCommand());
            GeneralRegistry.TrustedListCommands.add(command.getCommand());
            GeneralRegistry.ChanHalfOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());

        }
        for (GenericCommand command : GeneralRegistry.AuthCommands) {
            GeneralRegistry.GenericListCommands.add(command.getCommand());
            GeneralRegistry.TrustedListCommands.add(command.getCommand());
            GeneralRegistry.ChanHalfOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());

        }
        for (GenericCommand command : GeneralRegistry.TrustedCommands) {
            GeneralRegistry.TrustedListCommands.add(command.getCommand());
            GeneralRegistry.ChanHalfOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());
        }
        for (GenericCommand command : GeneralRegistry.AnonymonityCommands) {
            GeneralRegistry.TrustedListCommands.add(command.getCommand());
            GeneralRegistry.ChanHalfOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());

        }
        for (GenericCommand command : GeneralRegistry.ChanHalfOpCommands) {
            GeneralRegistry.ChanHalfOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());

        }
        for (GenericCommand command : GeneralRegistry.ChanOpCommands) {
            GeneralRegistry.ChanOpListCommands.add(command.getCommand());
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());

        }
        for (GenericCommand command : GeneralRegistry.ChanOwnerCommands) {
            GeneralRegistry.ChanOwnerListCommands.add(command.getCommand());
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());
        }
        for (GenericCommand command : GeneralRegistry.ChanFounderCommands) {
            GeneralRegistry.ChanFounderListCommands.add(command.getCommand());
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());
        }
        for (GenericCommand command : GeneralRegistry.GlobalCommands) {
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
            GeneralRegistry.GlobalListCommands.add(command.getCommand());
        }
        for (GenericCommand command : GeneralRegistry.ControllerCommands) {
            GeneralRegistry.ControllerListCommands.add(command.getCommand());
        }
    }
}
