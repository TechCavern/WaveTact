package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.annot.*;
import com.techcavern.wavetact.utils.eventListeners.CTCPListener;
import com.techcavern.wavetact.utils.eventListeners.ChanMsgListener;
import com.techcavern.wavetact.utils.eventListeners.JoinListener;
import com.techcavern.wavetact.utils.eventListeners.KickListener;
import com.techcavern.wavetact.utils.eventListeners.PrivMsgListener;
import com.techcavern.wavetact.utils.objects.CommandLine;
import com.techcavern.wavetact.utils.objects.FunObject;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.Annotation;
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
        addCommands(GeneralRegistry.AnonymonityCommands, AnonCMD.class);
        addCommands(GeneralRegistry.AuthCommands, AuthCMD.class);
        addCommands(GeneralRegistry.ChanFounderCommands, ChanFounderCMD.class);
        addCommands(GeneralRegistry.ChanHalfOpCommands, ChanHOPCMD.class);
        addCommands(GeneralRegistry.ChanOwnerCommands, ChanOWNCMD.class);
        addCommands(GeneralRegistry.ControllerCommands, ConCMD.class);
        addCommands(GeneralRegistry.FunCommands, FunCMD.class);
        addCommands(GeneralRegistry.GenericCommands, GenCMD.class);
        addCommands(GeneralRegistry.GlobalCommands, GloCMD.class);
        addCommands(GeneralRegistry.TrustedCommands, TruCMD.class);
    }

    public static void initializeCommandlines() {
        addCommandLines(GeneralRegistry.CommandLines, CMDLine.class);
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
    public static void addCommands(List<GenericCommand> list, Class<? extends Annotation> cl){
        io.github.asyncronous.mirrors.Package pkg = new io.github.asyncronous.mirrors.Package("io.github.asyncronous.mirrors");
        List<Class<?>> classes = pkg.getClasses(cl);
        for(Class<?> clss: classes){
            try{
                list.add(((GenericCommand) clss.newInstance()));
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void addCommandLines(List<CommandLine> list, Class<? extends Annotation> cl){
        io.github.asyncronous.mirrors.Package pkg = new io.github.asyncronous.mirrors.Package("io.github.asyncronous.mirrors");
        List<Class<?>> classes = pkg.getClasses(cl);
        for(Class<?> clss: classes){
            try{
                list.add(((CommandLine) clss.newInstance()));
            } catch(Exception e){
                e.printStackTrace();
            }
        }
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
