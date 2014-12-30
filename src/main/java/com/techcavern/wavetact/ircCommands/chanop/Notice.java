package com.techcavern.wavetact.ircCommands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanOPCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.IRCCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@ChanOPCMD
public class Notice extends IRCCommand {

    public Notice() {
        super(GeneralUtils.toArray("notice"), 10, "notice (message)", "Sends a notice to the channel", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        channel.send().notice(user.getNick() + ": " + StringUtils.join(args, " "));
    }
}