package com.techcavern.wavetact.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techcavern.wavetact.commands.BasicCommands;
import com.techcavern.wavetact.commands.CheckUserLevel;
import com.techcavern.wavetact.commands.SomethingAwesome;
import com.techcavern.wavetact.commands.chanop.*;
import com.techcavern.wavetact.commands.controller.Join;
import com.techcavern.wavetact.commands.controller.Lock;
import com.techcavern.wavetact.commands.trusted.Act;
import com.techcavern.wavetact.commands.trusted.CustomCMD;
import com.techcavern.wavetact.commands.trusted.Say;
import com.techcavern.wavetact.events.HighFive;
import com.techcavern.wavetact.events.KickRejoin;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.objects.JSONFile;
import com.techcavern.wavetact.objects.SimpleAction;
import com.techcavern.wavetact.objects.SimpleMessage;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.output.OutputChannel;
import org.pircbotx.output.OutputUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

//import com.techcavern.wavetact.commands.TestCommand;

public class IRCUtils {

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static PircBotX createbot(String g, String Name, List<String> channels, String Nick, String Server) throws Exception {
        System.out.println("Configuring " + Name);
        Builder<PircBotX> Net = new Configuration.Builder<>();
        Net.setName(Nick);
        Net.setLogin("WaveTact");
        Net.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
        Net.setServer(Server, 6667);
        for (String channel : channels) {
            Net.addAutoJoinChannel(channel);
        }
        Net.getListenerManager().addListener(new MessageListener());
        Net.getListenerManager().addListener(new HighFive());
        Net.getListenerManager().addListener(new KickRejoin());
        if (g != null) {
            Net.setNickservPassword(g);
        }
        //  Net.getListenerManager().addListener(new TestCommand());
        return new PircBotX(Net.buildConfiguration());
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
        new Topic();
        new Lock();

    }

    /*
        public static void RegisterExistingSimpleMessages() {



            for (Command Command : GeneralRegistry.SimpleMessage.) {
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
    */
    public static Command getCommand(String Command) {
        for (Command g : GeneralRegistry.Commands) {
            if (g.getCommandID().equalsIgnoreCase(Command)) {
                return g;
            }
        }
        return null;

    }

    public static SimpleMessage getSimpleMessage(String SimpleAction) {
        for (SimpleMessage g : GeneralRegistry.SimpleMessages) {
            if (g.getCommandID().equalsIgnoreCase(SimpleAction)) {
                return g;
            }
        }
        return null;

    }

    public static SimpleAction getSimpleAction(String SimpleAction) {
        for (SimpleAction g : GeneralRegistry.SimpleActions) {
            if (g.getCommandID().equalsIgnoreCase(SimpleAction)) {
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
                List<SimpleAction> actions = file.read(List.class);
                GeneralRegistry.SimpleActions.clear();
                GeneralRegistry.SimpleActions.addAll(actions);
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }
    public static void saveSimpleMessages() {
        JSONFile file = new JSONFile("SimpleMessages.json");
        try {
            file.write(GeneralRegistry.SimpleActions);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadSimpleMessages() {
        JSONFile file = new JSONFile("SimpleMessages.json");
        if (file.exists()) {
            try {
                List<SimpleAction> actions = file.read(List.class);
                GeneralRegistry.SimpleActions.clear();
                GeneralRegistry.SimpleActions.addAll(actions);
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }
}
