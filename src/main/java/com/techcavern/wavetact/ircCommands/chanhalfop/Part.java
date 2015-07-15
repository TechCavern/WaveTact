/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.chanhalfop;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.objects.NetRecord;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.techcavern.wavetactdb.Tables.SERVERS;

/**
 * @author jztech101
 */
@IRCCMD

public class Part extends IRCCommand {

    public Part() {
        super(GeneralUtils.toArray("part pa"), 7, "part (+)[channel]", "Parts a channel", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args.length < 1 || (args.length == 1 && args[0].equalsIgnoreCase(channel.getName()))) {
            channel.send().part();
            Registry.LastLeftChannel = channel.getName();
        } else {
            if (userPermLevel >= 20) {
                boolean permanent = false;
                if (args[0].startsWith("+")) {
                    args[0] = args[0].replace("+", "");
                    Registry.LastLeftChannel = args[0];
                    permanent = true;
                }
                Registry.MessageQueue.add(new NetRecord("PART " + args[0], network));
                if (permanent) {
                    Record networkRecord = DatabaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(network));
                    List<String> channels = new LinkedList<>(Arrays.asList(StringUtils.split(networkRecord.getValue(SERVERS.CHANNELS), ", ")));
                    channels.stream().filter(chan -> chan.equals(args[0])).forEach(channels::remove);
                    networkRecord.setValue(SERVERS.CHANNELS, StringUtils.join(channels, ", "));
                    DatabaseUtils.updateServer(networkRecord);
                }
            } else {
                ErrorUtils.sendError(user, "Permission denied");
            }
        }
    }
}
