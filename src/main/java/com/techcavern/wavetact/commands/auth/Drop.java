package com.techcavern.wavetact.commands.auth;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.AccountUtils;
import com.techcavern.wavetact.utils.objects.Account;
import com.techcavern.wavetact.utils.objects.AuthedUser;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class Drop extends GenericCommand {

    public Drop() {
        super(GeneralUtils.toArray("drop"), 0, "drop [password]", "drops a user", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!PermUtils.checkIfAccountEnabled(network)) {
            IRCUtils.sendError(user, "This network is set to " + GetUtils.getAuthType(network) + " Authentication");
            return;
        }
        AuthedUser authedUser = PermUtils.getAuthedUser(network, user.getNick());
        if (authedUser == null) {
            IRCUtils.sendError(user, "Error, you are not logged in");
        } else {
            Account account = AccountUtils.getAccount(authedUser.getAuthAccount());
            if (GeneralRegistry.encryptor.checkPassword(args[0], account.getAuthPassword())) {
                GeneralRegistry.AuthedUsers.remove(authedUser);
                GeneralRegistry.Accounts.remove(account);
                AccountUtils.saveAccounts();
                IRCUtils.sendMessage(user, network, channel, "Your account is now dropped", prefix);
            } else {
                IRCUtils.sendError(user, "Incorrect Password");
            }
        }
    }
}

