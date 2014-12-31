package com.techcavern.wavetact.ircCommands.auth;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.olddatabaseUtils.AccountUtils;
import com.techcavern.wavetact.objects.Account;
import com.techcavern.wavetact.objects.AuthedUser;
import com.techcavern.wavetact.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
@GenCMD
public class Authenticate extends IRCCommand {

    public Authenticate() {
        super(GeneralUtils.toArray("authenticate auth identify id login"), 0, "identify (username) [password]", "Identifies a user", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!PermUtils.checkIfAccountEnabled(network)) {
            ErrorUtils.sendError(user, "This network is set to " + IRCUtils.getAuthType(network) + " authentication");
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
        if (PermUtils.authUser(network, user.getNick()) != null) {
            ErrorUtils.sendError(user, "Error, you are already identified");

        } else {
            Account acc = AccountUtils.getAccount(userString);
            if (acc != null && Registry.encryptor.checkPassword(password, acc.getAuthPassword())) {
                Registry.AuthedUsers.add(new AuthedUser(IRCUtils.getNetworkNameByNetwork(network), userString, IRCUtils.getHostmask(network, user.getNick(), false)));
                IRCUtils.sendMessage(user, network, channel, "Identification successful", prefix);
            } else {
                ErrorUtils.sendError(user, "Unable to identify (incorrect user/password combination)");
            }
        }
    }
}

