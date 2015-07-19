package com.techcavern.wavetact.ircCommands.netadmin;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.NETWORKUSERPROPERTY;

@IRCCMD
public class NetworkUserProperty extends IRCCommand {

    public NetworkUserProperty() {
        super(GeneralUtils.toArray("networkuserproperty netuserprop nup"), 18, "networkuserproperty (+)(-)[user] [property] (value)", "creates, modifies or removes network-user properties", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String networkname = IRCUtils.getNetworkNameByNetwork(network);
        String account;
        boolean isModify = false;
        boolean isDelete = false;
        boolean viewonly = false;
        if (args.length < 3) {
            viewonly = true;
        }
        if (args[0].startsWith("-")) {
            account = args[0].replaceFirst("-", "");
            isDelete = true;
        } else if (args[0].startsWith("+")) {
            account = args[0].replaceFirst("\\+", "");
            isModify = true;
        } else {
            account = args[0];
        }
        String auth = PermUtils.authUser(network, account);
        if (auth != null) {
            account = auth;
        } else {
            IRCUtils.sendError(user, "User must be identified");
            return;
        }
        Record networkUserProperty = DatabaseUtils.getNetworkUserProperty(networkname, account, args[1]);
        if (networkUserProperty != null && (isDelete || isModify)) {
            if (isDelete) {
                DatabaseUtils.removeNetworkUserProperty(networkname, account, args[1]);
                IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "Property deleted", prefix);
            } else if (isModify) {
                if (viewonly)
                    IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "[" + account + "] " + args[1] + ": " + networkUserProperty.getValue(NETWORKUSERPROPERTY.VALUE), prefix);
                else {
                    networkUserProperty.setValue(NETWORKUSERPROPERTY.VALUE, args[2]);
                    DatabaseUtils.updateNetworkUserProperty(networkUserProperty);
                    IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "Property modified", prefix);
                }
            }
        } else if (networkUserProperty == null && !isDelete && !isModify) {
            DatabaseUtils.addNetworkUserProperty(networkname, account, args[1], args[2]);
            IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "Property added", prefix);
        } else {
            IRCUtils.sendError(user, "property already exists (If you were adding) or property does not exist");
        }

    }
}
