package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.AccountUtils;
import com.techcavern.wavetact.utils.objects.Account;
import com.techcavern.wavetact.utils.objects.AuthedUser;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@ConCMD
public class FDrop extends GenericCommand {

    public FDrop() {
        super(GeneralUtils.toArray("fdrop"), 9001, "fdrop [user]", "forcefully drops a user");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (!PermUtils.checkIfAccountEnabled(Bot)) {
            IRCUtils.sendError(user, "This network is set to " + GetUtils.getAuthType(Bot) + " Authentication");
            return;
        }
        AuthedUser authedUser = PermUtils.getAuthedUser(Bot, args[0]);
        if (authedUser != null) {
            GeneralRegistry.AuthedUsers.remove(authedUser);
        }
        Account account = AccountUtils.getAccount(args[0]);
        if (account != null) {
            GeneralRegistry.Accounts.remove(account);
            AccountUtils.saveAccounts();
            IRCUtils.sendMessage(user, channel, "Account dropped", isPrivate);
        } else {
            IRCUtils.sendError(user, "account does not exist");
        }
    }
}

