/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.chanhalfop;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import static com.techcavern.wavetactdb.Tables.*;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jztech101
 */
@IRCCMD

public class Part extends IRCCommand {

    public Part() {
        super(GeneralUtils.toArray("part pa"), 7, "part (+)[channel]", "Parts a channel", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args.length < 1) {
            channel.send().part();
            Registry.LastLeftChannel = channel.getName();
        } else {
            if (userPermLevel >= 9001) {
                boolean permanent = false;
                if (args[0].startsWith("+")) {
                    args[0] = args[0].replace("+", "");
                    Registry.LastLeftChannel = args[0];
                    permanent = true;
                }
                network.sendRaw().rawLine("PART " + args[0]);
                if (permanent) {
                    Record server = DatabaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(network));
                    List<String> channels = new LinkedList<>(Arrays.asList(StringUtils.split(server.getValue(SERVERS.CHANNELS), ", ")));
                    channels.stream().filter(chan -> chan.equals(args[0])).forEach(channels::remove);
                    server.setValue(SERVERS.CHANNELS, StringUtils.join(channels, ", "));
                }
            } else {
                ErrorUtils.sendError(user, "Permission denied");
            }
        }
    }
}
