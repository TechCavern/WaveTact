package com.techcavern.wavetact.ircCommands.chanadmin;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.CHANNELPROPERTY;
import static com.techcavern.wavetactdb.Tables.CHANNELUSERPROPERTY;

@IRCCMD
public class ChannelProperty extends IRCCommand {

    public ChannelProperty() {
        super(GeneralUtils.toArray("channelproperty chanprop set"), 18, "set (+)(-)[property] (value)", "creates, modifies or removes channel properties", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
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
        String auth = PermUtils.authUser(network, property);
        if (auth != null) {
            property = auth;
        }
        Record channelUserProperty = DatabaseUtils.getChannelProperty(networkname, channel.getName(),property);
        if(channelUserProperty != null && (isDelete || isModify)){
            if(isDelete){
                DatabaseUtils.removeChannelProperty(networkname, channel.getName(), property);
            }else if(isModify){
                if(viewonly)
                    IRCUtils.sendMessage(user, network, channel, args[0] + ": " +channelUserProperty.getValue(CHANNELPROPERTY.VALUE), prefix);
                channelUserProperty.setValue(CHANNELPROPERTY.VALUE, args[1]);
                DatabaseUtils.updateChannelUserProperty(channelUserProperty);
            }
        }else if (channelUserProperty == null && (!isDelete || !isModify)) {
                DatabaseUtils.addChannelProperty(networkname, channel.getName(), property, args[1]);
        }else{
            ErrorUtils.sendError(user, "Unknown user or unknown property");
        }

    }
}
