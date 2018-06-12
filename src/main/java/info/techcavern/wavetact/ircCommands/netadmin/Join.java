/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.techcavern.wavetact.ircCommands.netadmin;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static info.techcavern.wavetactdb.Tables.NETWORKS;


/**
 * @author jztech101
 */
@IRCCMD
public class Join extends IRCCommand {

    public Join() {
        super(GeneralUtils.toArray("join jo"), 20, "join (+)[channel]", "Joins a channel", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        boolean permanent = false;
        if (args[0].startsWith("+")) {
            args[0] = args[0].replace("+", "");
            permanent = true;
        }
        if (permanent) {
            Record netRecord = DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(network));
            netRecord.setValue(NETWORKS.CHANNELS, DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(network)).getValue(NETWORKS.CHANNELS) + ", " + args[0]);
            DatabaseUtils.updateNetwork(netRecord);
        }
        Registry.messageQueue.get(network).add("JOIN :" + args[0]);
    }
}
