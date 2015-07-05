package com.techcavern.wavetact.ircCommands.auth;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.AuthedUser;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.ACCOUNTS;
import static com.techcavern.wavetactdb.Tables.SERVERS;

@IRCCMD
public class Drop extends IRCCommand {

    public Drop() {
        super(GeneralUtils.toArray("drop"), 0, "drop [password]", "Drops a user", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!PermUtils.isAccountEnabled(network)) {
            ErrorUtils.sendError(user, "This network is set to " + DatabaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(network)).getValue(SERVERS.AUTHTYPE) + " authentication");
            return;
        }
        AuthedUser authedUser = PermUtils.getAuthedUser(network, user.getNick());
        if (authedUser == null) {
            ErrorUtils.sendError(user, "Error, you are not logged in");
        } else {
            Record account = DatabaseUtils.getAccount(authedUser.getAuthAccount());
            if (Registry.encryptor.checkPassword(args[0] + account.getValue(ACCOUNTS.RANDOMSTRING), account.getValue(ACCOUNTS.PASSWORD))) {
                DatabaseUtils.removeAccount(authedUser.getAuthAccount());
                IRCUtils.sendMessage(user, network, channel, "Your account is now dropped", prefix);
            } else {
                ErrorUtils.sendError(user, "Incorrect password");
            }
        }
    }
}

