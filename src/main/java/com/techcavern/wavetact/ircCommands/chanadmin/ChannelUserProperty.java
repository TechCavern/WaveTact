package com.techcavern.wavetact.ircCommands.chanadmin;

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

import static com.techcavern.wavetactdb.Tables.CHANNELUSERPROPERTY;

@IRCCMD
public class ChannelUserProperty extends IRCCommand {

    public ChannelUserProperty() {
        super(GeneralUtils.toArray("channeluserproperty chanuserprop setuser"), 18, "setuser (+)(-)[user] [property] (value)", "creates, modifies or removes channel-user properties", true);
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
            IRCUtils.sendError(user, network, channel, "User must be identified", prefix);
            return;
        }
        Record channelUserProperty = DatabaseUtils.getChannelUserProperty(networkname, channel.getName(), account, args[1]);
        if (channelUserProperty != null && (isDelete || isModify)) {
            if (isDelete) {
                DatabaseUtils.removeChannelUserProperty(networkname, channel.getName(), account, args[1]);
                IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "Property deleted", prefix);
            } else if (isModify) {
                if (viewonly)
                    IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "[" + account + "] " + args[1] + ": " + channelUserProperty.getValue(CHANNELUSERPROPERTY.VALUE), prefix);
                else {
                    channelUserProperty.setValue(CHANNELUSERPROPERTY.VALUE, GeneralUtils.buildMessage(2, args.length, args));
                    DatabaseUtils.updateChannelUserProperty(channelUserProperty);
                    IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "Property modified", prefix);
                }
            }
        } else if (channelUserProperty == null && !isDelete && !isModify) {
            DatabaseUtils.addChannelUserProperty(networkname, channel.getName(), account, args[1], GeneralUtils.buildMessage(2, args.length, args));
            IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "Property added", prefix);
        } else {
            IRCUtils.sendError(user, network, channel, "property already exists (If you were adding) or property does not exist", prefix);
        }

    }
}
