package com.techcavern.wavetact.utils;

import com.google.common.collect.Sets;
import java.nio.charset.Charset;
import java.util.List;

import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.techcavern.wavetact.commands.chanop.Ban;
import com.techcavern.wavetact.commands.chanop.Quiet;
import com.techcavern.wavetact.commands.Act;
import com.techcavern.wavetact.commands.BasicCommands;
import com.techcavern.wavetact.commands.CheckUserLevel;
import com.techcavern.wavetact.commands.CustomCMD;
import com.techcavern.wavetact.commands.chanop.Kick;
import com.techcavern.wavetact.commands.chanop.Mode;
import com.techcavern.wavetact.commands.chanop.Op;
import com.techcavern.wavetact.commands.chanop.Owner;
import com.techcavern.wavetact.commands.chanop.Part;
import com.techcavern.wavetact.commands.chanop.Protect;
import com.techcavern.wavetact.commands.chanop.Voice;
import com.techcavern.wavetact.commands.controller.Join;
import com.techcavern.wavetact.commands.Say;
import com.techcavern.wavetact.commands.SomethingAwesome;
import com.techcavern.wavetact.events.HighFive;
//import com.techcavern.wavetact.commands.TestCommand;
import com.techcavern.wavetact.events.KickRejoin;
import java.util.Set;
import org.pircbotx.output.OutputChannel;
import org.pircbotx.output.OutputUser;

public class IRCUtils {

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static PircBotX createbot(String g, String Name, List<String> channels, String Nick, String Server) throws Exception {
        System.out.println("Configuring " + Name);
        Builder<PircBotX> Net = new Configuration.Builder<PircBotX>();
        Net.setName(Nick);
        Net.setLogin("WaveTact");
        Net.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
        Net.setServer(Server, 6667);
        for (int i = 0; i < channels.size(); i++) {
            Net.addAutoJoinChannel(channels.get(i));
        }
        Net.getListenerManager().addListener(new MessageListener());
        Net.getListenerManager().addListener(new HighFive());
        Net.getListenerManager().addListener(new KickRejoin());
        if (g != null) {
            Net.setNickservPassword(g);
        }
        //  Net.getListenerManager().addListener(new TestCommand());
        PircBotX Bot = new PircBotX(Net.buildConfiguration());
        return Bot;
    }

    public static User getUserByNick(Channel c, String n) {
        for (User u : c.getUsers()) {
            if (u.getNick().equalsIgnoreCase(n)) {
                return u;
            }
        }
        return null;
    }

    public static void setMode(Channel c, PircBotX b, String d, User u) {
        OutputChannel o = new OutputChannel(b, c);

        if (u != null) {
            o.setMode(d, u.getHostmask());
        } else {
            o.setMode(d);
        }
    }

    public static void SendNotice(PircBotX b, User u, String s) {
        OutputUser x = new OutputUser(b, u);
        x.notice(s);
    }

    public static void RegisterCommands() {
        System.out.println("Registering Commands");
        new Act();
        new Say();
        new SomethingAwesome();
        new Ban();
        new Quiet();
        new Kick();
        new Mode();
        new Op();
        new Owner();
        new Part();
        new Protect();
        new Voice();
        new Join();
        new CustomCMD();
        new CheckUserLevel();
        new BasicCommands();

    }

    public static void RegisterExistingSimpleMessages() {
        Config config = new Config("SimpleMessage.json");

        GeneralRegistry.SimpleMessage = new Gson().fromJson(config.getText(), new TypeToken<Set<SimpleMessage>>() {
        }.getType());

        if (GeneralRegistry.SimpleMessage == null) {
            GeneralRegistry.SimpleMessage = Sets.newConcurrentHashSet();
        }

        for (Command Command : GeneralRegistry.SimpleMessage) {
            GeneralRegistry.Commands.add(Command);
        }
    }

    public static void RegisterExistingSimpleActions() {
        Config config = new Config("SimpleAction.json");

        GeneralRegistry.SimpleAction = new Gson().fromJson(config.getText(), new TypeToken<Set<SimpleMessage>>() {
        }.getType());

        if (GeneralRegistry.SimpleAction == null) {
            GeneralRegistry.SimpleAction = Sets.newConcurrentHashSet();
        }

        for (Command Command : GeneralRegistry.SimpleAction) {
            GeneralRegistry.Commands.add(Command);
        }
    }

    public static Command getCommand(String Command) {
        for (Command g : GeneralRegistry.Commands) {
            if (g.getcomid().equalsIgnoreCase(Command)) {
                return g;
            }
        }
        return null;

    }
}
