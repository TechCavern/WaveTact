package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanOPCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@ChanOPCMD
public class Notice extends GenericCommand {

    public Notice() {
        super(GeneralUtils.toArray("notice not"), 10, "notice (message)", "sends a notice to the channel");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        channel.send().notice(user.getNick() + ": " + StringUtils.join(args, " "));
    }
}