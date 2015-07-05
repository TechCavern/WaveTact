package com.techcavern.wavetact.ircCommands.utils;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetactdb.tables.Servers;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.SERVERS;

@IRCCMD
public class Admins extends IRCCommand {

    public Admins() {
        super(GeneralUtils.toArray("admins admin netadmin netadmins"), 0, "admins", "List network administrators", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        IRCUtils.sendMessage(user, network, channel,DatabaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(network)).getValue(SERVERS.NETWORKADMINS), prefix);
    }
}
