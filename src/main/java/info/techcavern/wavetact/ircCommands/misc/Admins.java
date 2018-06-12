package info.techcavern.wavetact.ircCommands.misc;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static info.techcavern.wavetactdb.Tables.NETWORKS;

@IRCCMD
public class Admins extends IRCCommand {

    public Admins() {
        super(GeneralUtils.toArray("admins admin adm netadmin netadmins"), 1, "admins", "List network administrators", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String[] admins = StringUtils.split(DatabaseUtils.getNetwork(IRCUtils.getNetworkNameByNetwork(network)).getValue(NETWORKS.NETWORKADMINS), ",");
        for (int i = 0; i < admins.length; i++) {
            admins[i] = IRCUtils.noPing(admins[i].replace(" ", ""));
        }
        IRCUtils.sendMessage(user, network, channel, StringUtils.join(admins, ", "), prefix);
    }
}
