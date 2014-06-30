package com.techcavern.wavetact.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.events.KickRejoin;
import com.techcavern.wavetact.objects.*;
import com.techcavern.wavetact.thread.CommandCollection;
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
import java.util.stream.Collectors;

//import com.techcavern.wavetact.Commands.Commands.TestCommand;

public class IRCUtils {

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static PircBotX createbot(String g, String Name, List<String> channels, String Nick, String Server) {
        System.out.println("Configuring " + Name);
        Builder<PircBotX> Net = new Configuration.Builder<PircBotX>();
        Net.setName(Nick);
        Net.setLogin("WaveTact");
        Net.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
        Net.setServer(Server, 6667);
        channels.forEach(Net::addAutoJoinChannel);
        Net.getListenerManager().addListener(new MessageListener());
    //    Net.getListenerManager().addListener(new HighFive());
        Net.getListenerManager().addListener(new KickRejoin());
        if (g != null) {
            Net.setNickservPassword(g);
        }
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
            if (c.getUserBot().getServer().equalsIgnoreCase(n)) {
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
                List<LinkedTreeMap> actions = file.read(List.class);
                GeneralRegistry.SimpleActions.clear();
                GeneralRegistry.SimpleActions.addAll(actions.stream().map(act -> new SimpleAction((String) act.get("comid"),
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
        try{
            GeneralRegistry.COMMANDS.addAll(GeneralRegistry.TASKS.submit(new CommandCollection("com.techcavern.wavetact.commands")).get());
        } catch(Exception ex){
            ex.printStackTrace(System.err);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadSimpleMessages() {
        JSONFile file = new JSONFile("SimpleMessages.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> messages = file.read();
                GeneralRegistry.SimpleMessages.clear();

                GeneralRegistry.SimpleMessages.addAll(messages.stream().map(msg -> new SimpleMessage((String) msg.get("comid"),
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
            if (x.getHostmask() == u) {
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
                        (String) act.get("channel"),
                        (String) act.get("type"),
                        (long) act.get("time"))).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }
    public static UTime getQuietTime(String u) {
        for (UTime x : GeneralRegistry.QuietTimes) {
            if (x.getHostmask() == u) {
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
                        (String) act.get("channel"),
                        (String) act.get("type"),
                        (long) act.get("time"))).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }
}
