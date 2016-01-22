package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.ircCommands.chanadmin.ChannelUserProperty;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetactdb.tables.Channeluserproperty;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

@IRCCMD
public class ListPermLevels extends IRCCommand {

    public ListPermLevels() {
        super(GeneralUtils.toArray("listpermlevels listperms lpl"), 0, "listpermlevels", "List Manually Set Permlevels of a Channel", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        List<String> msg = new ArrayList<>();
        Result<Record> records = DatabaseUtils.getChannelUserProperty(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), "permlevel");
        if (records.size() > 0) {
            for (Record rec :records) {
                msg.add(rec.getValue(Channeluserproperty.CHANNELUSERPROPERTY.USER) + "[" + rec.getValue(Channeluserproperty.CHANNELUSERPROPERTY.VALUE) + "]");
            }
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(msg," - "), prefix);
        }else{
            IRCUtils.sendError(user, network, channel, "No Manually Set Perm Levels Found", prefix);
        }
    }
}
