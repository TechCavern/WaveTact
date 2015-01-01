package com.techcavern.wavetact.ircCommands.auth;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.UUID;

import static com.techcavern.wavetactdb.Tables.ACCOUNTS;
import static com.techcavern.wavetactdb.Tables.SERVERS;

@IRCCMD
public class SetPass extends IRCCommand {

    public SetPass() {
        super(GeneralUtils.toArray("setpassword setpass changepassword changepass"), 0, "setpass [oldpass] [newpass]", "Sets password", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!PermUtils.isAccountEnabled(network)) {
            ErrorUtils.sendError(user, "This network is set to " + DatabaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(network)).getValue(SERVERS.AUTHTYPE) + " authentication");
            return;
        }
        String AuthUser = PermUtils.authUser(network, user.getNick());
        if (AuthUser != null) {
            Record account = DatabaseUtils.getAccount(args[0]);
            if (Registry.encryptor.checkPassword(args[0] + account.getValue(ACCOUNTS.RANDOMSTRING), account.getValue(ACCOUNTS.PASSWORD))) {
                account.setValue(ACCOUNTS.RANDOMSTRING, UUID.randomUUID().toString());
                account.setValue(ACCOUNTS.PASSWORD, Registry.encryptor.encryptPassword(args[1] + ACCOUNTS.RANDOMSTRING));
                IRCUtils.sendMessage(user, network, channel, "Password changed successfully", prefix);
            } else {
                ErrorUtils.sendError(user, "Incorrect password");
            }
        } else {
            ErrorUtils.sendError(user, "Error - you must be identified to use this command");
        }
    }
}

