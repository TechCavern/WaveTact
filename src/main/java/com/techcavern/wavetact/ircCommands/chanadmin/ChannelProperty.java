package com.techcavern.wavetact.ircCommands.chanadmin;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.CHANNELPROPERTY;

@IRCCMD
public class ChannelProperty extends IRCCommand {

    public ChannelProperty() {
        super(GeneralUtils.toArray("channelproperty chanprop set"), 18, "set (+)(-)[property] (value)", "creates, modifies or removes channel properties", true);
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
        Record channelProperty = DatabaseUtils.getChannelProperty(networkname, channel.getName(), property);
        if (channelProperty != null && (isDelete || isModify)) {
            if (isDelete) {
                DatabaseUtils.removeChannelProperty(networkname, channel.getName(), property);
                IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "Property deleted", prefix);
            } else if (isModify) {
                if (viewonly)
                    IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "[" + property + "] " + channelProperty.getValue(CHANNELPROPERTY.VALUE), prefix);
                else {
                    channelProperty.setValue(CHANNELPROPERTY.VALUE, GeneralUtils.buildMessage(1, args.length, args));
                    DatabaseUtils.updateChannelProperty(channelProperty);
                    IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "Property modified", prefix);
                }
            }
        } else if (channelProperty == null && !isDelete && !isModify) {
            DatabaseUtils.addChannelProperty(networkname, channel.getName(), property, GeneralUtils.buildMessage(1, args.length, args));
            IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "Property added", prefix);
        } else {
            IRCUtils.sendError(user, network, channel, "property already exists (If you were adding) or property does not exist", prefix);
        }

    }
}
