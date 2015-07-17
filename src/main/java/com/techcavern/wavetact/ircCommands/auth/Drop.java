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
            ErrorUtils.sendError(user, "This network is set to " + DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(network)).getValue(NETWORKS.AUTHTYPE) + " authentication");
            return;
        }
        String authedUser = PermUtils.authUser(network, user.getNick());
        Record account = DatabaseUtils.getAccount(authedUser);
        if (Registry.encryptor.checkPassword(args[0] + account.getValue(ACCOUNTS.RANDOMSTRING), account.getValue(ACCOUNTS.PASSWORD))) {
            if (authedUser != null) {
                Registry.networkBot.keySet().stream().filter(net -> PermUtils.isAccountEnabled(IRCUtils.getNetworkByNetworkName(net))).forEach(net -> {
                    Registry.authedUsers.get(IRCUtils.getNetworkByNetworkName(net)).keySet().stream().filter(key -> Registry.authedUsers.get(IRCUtils.getNetworkByNetworkName(net)).get(key).equals(authedUser)).forEach(key ->
                                    Registry.authedUsers.get(IRCUtils.getNetworkByNetworkName(net)).remove(key)
                    );
                    DatabaseUtils.removeNetworkUserPropertyByUser(net, authedUser);
                    DatabaseUtils.removeChannelUserPropertyByUser(net, authedUser);
                });
                DatabaseUtils.removeAccount(authedUser);
            }
            IRCUtils.sendMessage(user, network, channel, "Your account is now dropped", prefix);
            IRCUtils.sendLogChanMsg(network, "[ACCOUNT DROPPED] " + IRCUtils.noPing(user.getNick()));
        } else {
            ErrorUtils.sendError(user, "Incorrect password");
        }
    }
}

