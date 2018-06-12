package info.techcavern.wavetact.ircCommands.auth;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.*;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static info.techcavern.wavetactdb.Tables.ACCOUNTS;
import static info.techcavern.wavetactdb.Tables.NETWORKS;

@IRCCMD
public class Authenticate extends IRCCommand {

    public Authenticate() {
        super(GeneralUtils.toArray("authenticate auth identify id login"), 0, "identify (username) [password]", "Identifies a user", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!PermUtils.isAccountEnabled(network)) {
            IRCUtils.sendError(user, network, channel, "This network is set to " + DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(network)).getValue(NETWORKS.AUTHTYPE) + " authentication", prefix);
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
            IRCUtils.sendError(user, network, channel, "Error, you are already identified", prefix);
        } else {
            Record account = DatabaseUtils.getAccount(userString);
            if (account != null && Registry.encryptor.checkPassword(password + account.getValue(ACCOUNTS.RANDOMSTRING), account.getValue(ACCOUNTS.PASSWORD))) {
                Registry.authedUsers.get(network).put(IRCUtils.getHostmask(network, user.getNick(), false), userString);
                IRCUtils.sendMessage(user, network, channel, "Identification successful", prefix);
                IRCUtils.sendLogChanMsg(network, "[AUTH SUCCESS] " + IRCUtils.noPing(user.getNick()));
            } else {
                IRCUtils.sendError(user, network, channel, "Unable to identify (incorrect user/password combination)", prefix);
                IRCUtils.sendLogChanMsg(network, "[AUTH FAILURE] " + IRCUtils.noPing(user.getNick()));
            }
        }
    }
}

