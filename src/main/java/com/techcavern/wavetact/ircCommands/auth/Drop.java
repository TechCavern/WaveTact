package com.techcavern.wavetact.ircCommands.auth;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.ACCOUNTS;
import static com.techcavern.wavetactdb.Tables.NETWORKS;

@IRCCMD
public class Drop extends IRCCommand {

    public Drop() {
        super(GeneralUtils.toArray("drop"), 1, "drop [password]", "Drops a user", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!PermUtils.isAccountEnabled(network)) {
            IRCUtils.sendError(user, network, channel, "This network is set to " + DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(network)).getValue(NETWORKS.AUTHTYPE) + " authentication", prefix);
            return;
        }
        String authedUser = PermUtils.authUser(network, user.getNick());
        Record account = DatabaseUtils.getAccount(authedUser);
        if (Registry.encryptor.checkPassword(args[0] + account.getValue(ACCOUNTS.RANDOMSTRING), account.getValue(ACCOUNTS.PASSWORD))) {
            if (authedUser != null) {
                Registry.networks.inverse().keySet().stream().filter(net -> PermUtils.isAccountEnabled(net)).forEach(net -> {
                    Registry.authedUsers.get(net).keySet().stream().filter(key -> Registry.authedUsers.get(net).get(key).equals(authedUser)).forEach(key ->
                                    Registry.authedUsers.get(net).remove(key)
                    );
                    DatabaseUtils.removeNetworkUserPropertyByUser(IRCUtils.getNetworkNameByNetwork(net), authedUser);
                    DatabaseUtils.removeChannelUserPropertyByUser(IRCUtils.getNetworkNameByNetwork(net), authedUser);
                });
                DatabaseUtils.removeAccount(authedUser);
            }
            IRCUtils.sendMessage(user, network, channel, "Your account is now dropped", prefix);
            IRCUtils.sendLogChanMsg(network, "[ACCOUNT DROPPED] " + IRCUtils.noPing(user.getNick()));
        } else {
            IRCUtils.sendError(user, network, channel, "Incorrect password", prefix);
        }
    }
}

