/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.techcavern.wavetact.ircCommands.chanhalfop;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.xbill.DNS.SRVRecord;
import org.xbill.DNS.Type;

/**
 * @author jztech101
 */
@IRCCMD

public class Mode extends IRCCommand {

    public Mode() {
        super(GeneralUtils.toArray("mode mod"), 7, "mode [modes to set]", "Sets a mode on the channel", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if(args.length > 0)
        IRCUtils.setMode(channel, network, StringUtils.join(args, " "), null);
        else
            IRCUtils.sendMessage(user, network, channel, channel.getMode(), prefix);

    }
}
