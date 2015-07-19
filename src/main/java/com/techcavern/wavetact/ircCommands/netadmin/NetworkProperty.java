package com.techcavern.wavetact.ircCommands.netadmin;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.NETWORKPROPERTY;

@IRCCMD
public class NetworkProperty extends IRCCommand {

    public NetworkProperty() {
        super(GeneralUtils.toArray("networkproperty npr netprop"), 20, "netprop (+)(-)[property] (value)", "creates, modifies or removes network properties", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String networkname = IRCUtils.getNetworkNameByNetwork(network);
        String property;
        boolean isModify = false;
        boolean isDelete = false;
        boolean viewonly = false;
        if (args.length < 2) {
            viewonly = true;
        }
        if (args[0].startsWith("-")) {
            property = args[0].replaceFirst("-", "");
            isDelete = true;
        } else if (args[0].startsWith("+")) {
            property = args[0].replaceFirst("\\+", "");
            isModify = true;
        } else {
            property = args[0];
        }
        Record networkProperty = DatabaseUtils.getNetworkProperty(networkname, property);
        if (networkProperty != null && (isDelete || isModify)) {
            if (isDelete) {
                DatabaseUtils.removeNetworkProperty(networkname, property);
                IRCUtils.sendMessage(user, network, channel, "Property deleted", prefix);
            } else if (isModify) {
                if (viewonly)
                    IRCUtils.sendMessage(user, network, channel, property + ": " + networkProperty.getValue(NETWORKPROPERTY.VALUE), prefix);
                else {
                    networkProperty.setValue(NETWORKPROPERTY.VALUE, args[1]);
                    DatabaseUtils.updateNetworkProperty(networkProperty);
                    IRCUtils.sendMessage(user, network, channel, "Property modified", prefix);
                }
            }
        } else if (networkProperty == null && !isDelete && !isModify) {
            DatabaseUtils.addNetworkProperty(networkname, property, args[1]);
            IRCUtils.sendMessage(user, network, channel, "Property added", prefix);
        } else {
            IRCUtils.sendError(user, "property already exists (If you were adding) or property does not exist");
        }

    }
}
