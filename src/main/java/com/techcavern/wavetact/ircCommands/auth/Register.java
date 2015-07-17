package com.techcavern.wavetact.ircCommands.auth;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.UUID;

import static com.techcavern.wavetactdb.Tables.NETWORKS;

@IRCCMD
public class Register extends IRCCommand {

    public Register() {
        super(GeneralUtils.toArray("register reg"), 0, "register (username) [password]", "Registers a user", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!PermUtils.isAccountEnabled(network)) {
            ErrorUtils.sendError(user, "This network is set to " + DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(network)).getValue(NETWORKS.AUTHTYPE) + " authentication");
            return;
        }
        String userString;
        String password;
        if (args.length < 2) {
            userString = user.getNick();
            password = args[0];
        } else {
            userString = args[0];
            password = args[1];
        }
        if (DatabaseUtils.getAccount(userString) != null) {
            ErrorUtils.sendError(user, "Error, account already exists!");

        } else {
            String randomString = UUID.randomUUID().toString();
            DatabaseUtils.addAccount(userString, Registry.encryptor.encryptPassword(password + randomString), randomString);
            Registry.authedUsers.get(network).put(IRCUtils.getHostmask(network, user.getNick(), false), userString);
            IRCUtils.sendMessage(user, network, channel, "You are now registered", prefix);
            IRCUtils.sendLogChanMsg(network, "[REGISTRATION] " + IRCUtils.noPing(user.getNick()));
        }
    }
}

