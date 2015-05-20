package com.techcavern.wavetact.ircCommands.chanadmin;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import static com.techcavern.wavetactdb.Tables.*;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class ChannelUserProperty extends IRCCommand {

    public ChannelUserProperty() {
        super(GeneralUtils.toArray("channeluserproperty chanuserprop setuser"), 18, "setuser (+)(-)[user] [property] (value)", "creates, modifies or removes channel-user properties", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
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
        }
        Record channelUserProperty = DatabaseUtils.getChannelUserProperty(networkname, channel.getName(),account, args[1]);
        if(channelUserProperty != null && (isDelete || isModify)){
            if(isDelete){
                DatabaseUtils.removeChannelUserProperty(networkname, channel.getName(),account,args[1]);
                IRCUtils.sendMessage(user, network, channel, "Property deleted", prefix);
            }else if(isModify){
                if(viewonly)
                    IRCUtils.sendMessage(user, network, channel, account + args[1] + ": " +channelUserProperty.getValue(CHANNELUSERPROPERTY.VALUE), prefix);
                else {
                    channelUserProperty.setValue(CHANNELUSERPROPERTY.VALUE, args[2]);
                    DatabaseUtils.updateChannelUserProperty(channelUserProperty);
                    IRCUtils.sendMessage(user, network, channel, "Property modified", prefix);
                }
            }
        }else if (channelUserProperty == null && !isDelete && !isModify) {
                DatabaseUtils.addChannelUserProperty(networkname, channel.getName(), account, args[1], args[2]);
            IRCUtils.sendMessage(user, network, channel, "Property added", prefix);
        }else{
            ErrorUtils.sendError(user, "Unknown user or unknown property");
        }

    }
}
