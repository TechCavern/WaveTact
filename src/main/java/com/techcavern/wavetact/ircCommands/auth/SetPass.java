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
import static com.techcavern.wavetactdb.Tables.NETWORKS;

@IRCCMD
public class SetPass extends IRCCommand {

    public SetPass() {
        super(GeneralUtils.toArray("setpassword setpass changepassword changepass"), 1, "setpass [oldpass] [newpass]", "Sets password", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!PermUtils.isAccountEnabled(network)) {
            IRCUtils.sendError(user, network, channel, "This network is set to " + DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(network)).getValue(NETWORKS.AUTHTYPE) + " authentication", prefix);
            return;
        }
        String AuthUser = PermUtils.authUser(network, user.getNick());
        Record account = DatabaseUtils.getAccount(AuthUser);
        if (Registry.encryptor.checkPassword(args[0] + account.getValue(ACCOUNTS.RANDOMSTRING), account.getValue(ACCOUNTS.PASSWORD))) {
            account.setValue(ACCOUNTS.RANDOMSTRING, UUID.randomUUID().toString());
            account.setValue(ACCOUNTS.PASSWORD, Registry.encryptor.encryptPassword(args[1] + account.getValue(ACCOUNTS.RANDOMSTRING)));
            DatabaseUtils.updateAccount(account);
            IRCUtils.sendMessage(user, network, channel, "Password changed successfully", prefix);
            IRCUtils.sendLogChanMsg(network, "[PASS CHANGE] " + IRCUtils.noPing(user.getNick()));
        } else {
            IRCUtils.sendError(user, network, channel, "Incorrect password", prefix);
        }
    }
}

