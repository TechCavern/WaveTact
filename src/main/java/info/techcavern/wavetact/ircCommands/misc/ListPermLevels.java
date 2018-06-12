package info.techcavern.wavetact.ircCommands.misc;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.ircCommands.chanadmin.ChannelUserProperty;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.*;
import info.techcavern.wavetactdb.tables.Channeluserproperty;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.HashSet;
import java.util.Set;

@IRCCMD
public class ListPermLevels extends IRCCommand {

    public ListPermLevels() {
        super(GeneralUtils.toArray("listpermlevels listperms lpl"), 0, "listpermlevels", "List Manually Set Permlevels of a Channel", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Set<String> msg = new HashSet<>();
        Result<Record> records = DatabaseUtils.getChannelUserProperty(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), "permlevel");
        if (records.size() > 0) {
            for (Record rec :records) {
                msg.add(IRCUtils.noPing(rec.getValue(Channeluserproperty.CHANNELUSERPROPERTY.USER)) + "[" + rec.getValue(Channeluserproperty.CHANNELUSERPROPERTY.VALUE) + "]");
            }
            if(isPrivate)
                IRCUtils.sendMessage(user, network, null, StringUtils.join(msg," - "), prefix);
            else
                IRCUtils.sendMessage(user, network, channel, StringUtils.join(msg," - "), prefix);
        }else{
            if(isPrivate)
                IRCUtils.sendError(user, network, null, "No Manually Set Perm Levels Found", prefix);
            else
                IRCUtils.sendError(user, network, channel, "No Manually Set Perm Levels Found", prefix);
        }
    }
}
