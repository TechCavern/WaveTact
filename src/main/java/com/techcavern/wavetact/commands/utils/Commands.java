package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * Created by jztech101 on 6/23/14.
 */

@SuppressWarnings("ALL")
@CMD
@GenCMD
public class Commands extends GenericCommand {

    public Commands() {
        super(GeneralUtils.toArray("commands list cmds"), 0, null, "takes 0 arguments, returns list of Commands");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {


        if (UserPermLevel >= 9001) {
            IRCUtils.sendMessage(user, channel, StringUtils.join(GeneralRegistry.ControllerListCommands, ", "), isPrivate);
        } else if (UserPermLevel >= 20) {
            IRCUtils.sendMessage(user, channel, StringUtils.join(GeneralRegistry.GlobalListCommands, ", "), isPrivate);
        } else if (UserPermLevel >= 18) {
            IRCUtils.sendMessage(user, channel, StringUtils.join(GeneralRegistry.ChanFounderListCommands, ", "), isPrivate);
        } else if (UserPermLevel >= 15) {
            IRCUtils.sendMessage(user, channel, StringUtils.join(GeneralRegistry.ChanOwnerListCommands, ", "), isPrivate);
        } else if (UserPermLevel >= 13) {
            IRCUtils.sendMessage(user, channel, StringUtils.join(GeneralRegistry.ChanOpListCommands, ", "), isPrivate);
        } else if (UserPermLevel >= 10) {
            IRCUtils.sendMessage(user, channel, StringUtils.join(GeneralRegistry.ChanOpListCommands, ", "), isPrivate);
        } else if (UserPermLevel >= 7) {
            IRCUtils.sendMessage(user, channel, StringUtils.join(GeneralRegistry.ChanHalfOpListCommands, ", "), isPrivate);
        } else if (UserPermLevel >= 5) {
            IRCUtils.sendMessage(user, channel, StringUtils.join(GeneralRegistry.TrustedListCommands, ", "), isPrivate);
        } else {
            IRCUtils.sendMessage(user, channel, StringUtils.join(GeneralRegistry.GenericListCommands, ", "), isPrivate);
        }

    }
}

