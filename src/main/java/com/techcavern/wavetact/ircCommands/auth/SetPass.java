package com.techcavern.wavetact.ircCommands.auth;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.olddatabaseUtils.AccountUtils;
import com.techcavern.wavetact.objects.Account;
import com.techcavern.wavetact.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
@GenCMD
public class SetPass extends IRCCommand {

    public SetPass() {
        super(GeneralUtils.toArray("setpassword setpass changepassword changepass"), 0, "setpass [oldpass] [newpass]", "Sets password", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!PermUtils.checkIfAccountEnabled(network)) {
            ErrorUtils.sendError(user, "This network is set to " + GetUtils.getAuthType(network) + " authentication");
            return;
        }
        String AuthUser = PermUtils.authUser(network, user.getNick());
        if (AuthUser != null) {
            Account acc = AccountUtils.getAccount(AuthUser);
            if (Registry.encryptor.checkPassword(args[0], acc.getAuthPassword())) {
                acc.setAuthPassword(Registry.encryptor.encryptPassword(args[1]));
                AccountUtils.saveAccounts();
                IRCUtils.sendMessage(user, network, channel, "Password changed successfully", prefix);
            } else {
                ErrorUtils.sendError(user, "Incorrect password");
            }
        } else {
            ErrorUtils.sendError(user, "Error - you must be identified to use this command");
        }
    }
}

