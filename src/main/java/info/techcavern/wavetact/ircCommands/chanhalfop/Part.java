/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.techcavern.wavetact.ircCommands.chanhalfop;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

import static info.techcavern.wavetactdb.Tables.NETWORKS;

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
            Registry.lastLeftChannel.put(network, channel.getName());
        } else {
            if (userPermLevel >= 20) {
                boolean permanent = false;
                if (args[0].startsWith("+")) {
                    args[0] = args[0].replace("+", "");
                    permanent = true;
                }
                Registry.lastLeftChannel.put(network, args[0]);
                Registry.messageQueue.get(network).add("PART " + args[0]);
                if (permanent) {
                    Record netRecord = DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(network));
                    Set<String> channels = new HashSet<>(Arrays.asList(StringUtils.split(netRecord.getValue(NETWORKS.CHANNELS), ", ")));
                    channels.remove(args[0]);
                    netRecord.setValue(NETWORKS.CHANNELS, StringUtils.join(channels, ", "));
                    DatabaseUtils.updateNetwork(netRecord);
                }
            } else {
                IRCUtils.sendError(user, network, channel, "Permission denied", prefix);
            }
        }
    }
}
