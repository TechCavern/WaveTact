package com.techcavern.wavetact.ircCommands.utils;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Arrays;
import java.util.List;

import static com.techcavern.wavetactdb.Tables.SERVERS;

@IRCCMD
public class Admins extends IRCCommand {

    public Admins() {
        super(GeneralUtils.toArray("admins admin netadmin netadmins"), 0, "admins", "List network administrators", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String[] admins = StringUtils.split(DatabaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(network)).getValue(SERVERS.NETWORKADMINS), ",");
        for (int i = 0; i < admins.length; i++) {
            admins[i] = GeneralUtils.replaceVowelsWithAccents(admins[i].replace(" ", ""));
        }
        IRCUtils.sendMessage(user, network, channel, StringUtils.join(admins, ", "), prefix);
    }
}
