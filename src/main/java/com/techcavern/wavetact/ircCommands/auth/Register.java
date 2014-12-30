package com.techcavern.wavetact.ircCommands.auth;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.AccountUtils;
import com.techcavern.wavetact.objects.Account;
import com.techcavern.wavetact.objects.AuthedUser;
import com.techcavern.wavetact.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class Register extends IRCCommand {

    public Register() {
        super(GeneralUtils.toArray("register reg"), 0, "register (username) [password]", "Registers a user", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!PermUtils.checkIfAccountEnabled(network)) {
            ErrorUtils.sendError(user, "This network is set to " + GetUtils.getAuthType(network) + " authentication");
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
        if (AccountUtils.getAccount(userString) != null || PermUtils.authUser(network, user.getNick()) != null) {
            ErrorUtils.sendError(user, "Error, you are already registered");

        } else {
            Registry.Accounts.add(new Account(userString, Registry.encryptor.encryptPassword(password)));
            AccountUtils.saveAccounts();
            Registry.AuthedUsers.add(new AuthedUser(GetUtils.getNetworkNameByNetwork(network), userString, IRCUtils.getHostmask(network, user.getNick(), false)));
            IRCUtils.sendMessage(user, network, channel, "You are now registered", prefix);
        }
    }
}

