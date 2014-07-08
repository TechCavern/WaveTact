package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.objects.*;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * Created by jztech101 on 7/4/14.
 */
public class GetUtils {

    public static User getUserByNick(Channel channelObject, String nick) {
        for (User u : channelObject.getUsers()) {
            if (u.getNick().equalsIgnoreCase(nick)) {
                return u;
            }
        }
        return null;
    }


    public static Channel getChannelbyName(PircBotX botObject, String channelName) {
        for (Channel u : botObject.getUserBot().getChannels()) {
            if (u.getName().equalsIgnoreCase(channelName)) {
                return u;
            }
        }
        return null;
    }

    public static PircBotX getBotByNetwork(String network) {
        for (PircBotX c : GeneralRegistry.WaveTact.getBots()) {
            if (c.getServerInfo().getNetwork().equals(network)) {
                return c;
            }
        }
        return null;
    }

    public static GenericCommand getCommand(String Command) {
        for (GenericCommand g : GeneralRegistry.GenericCommands) {
            for(String commandid : g.getCommandID()){
                if(commandid.equalsIgnoreCase(Command)){
                    return g;
                }
            }
        }
        for (GenericCommand g : GeneralRegistry.TrustedCommands) {
            for(String commandid : g.getCommandID()){
                if(commandid.equalsIgnoreCase(Command)){
                    return g;
                }
            }
        }
        for (GenericCommand g : GeneralRegistry.ControllerCommands) {
            for(String commandid : g.getCommandID()){
                if(commandid.equalsIgnoreCase(Command)){
                    return g;
                }
            }
        }
        for (GenericCommand g : GeneralRegistry.ChanOwnerCommands) {
            if (g.getCommand().equalsIgnoreCase(Command)) {
                return g;
            }
        }
        for (GenericCommand g : GeneralRegistry.ChanOpCommands) {
            for(String commandid : g.getCommandID()){
                if(commandid.equalsIgnoreCase(Command)){
                    return g;
                }
            }
        }
        for (GenericCommand g : GeneralRegistry.ChanHalfOpCommands) {
            if (g.getCommand().equalsIgnoreCase(Command)) {
                return g;
            }
        }
        for (GenericCommand g : GeneralRegistry.AnonymonityCommands) {
            for(String commandid : g.getCommandID()){
                if(commandid.equalsIgnoreCase(Command)){
                    return g;
                }
            }
        }
        for (GenericCommand g : GeneralRegistry.ChanFounderCommands) {
            for(String commandid : g.getCommandID()){
                if(commandid.equalsIgnoreCase(Command)){
                    return g;
                }
            }
        }
        return null;

    }

    public static String getCommandChar(PircBotX botObject) {
        for (CommandChar d : GeneralRegistry.CommandChars) {
            if (d.getBot() == botObject) {
                return d.getCommandChar();
            }
        }
        return null;
    }
    public static UTime getTopic(String channelName, String networkName) {
        for (int i = GeneralRegistry.Topic.size()-1; i > -1; i-- ) {
            UTime x = GeneralRegistry.Topic.get(i);
            if (x.getChannelName().equalsIgnoreCase(channelName) && x.getNetworkName().equalsIgnoreCase(networkName)) {
                return x;
            }
        }
        return null;
    }

}
