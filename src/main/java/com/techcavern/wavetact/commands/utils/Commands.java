package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import javax.xml.bind.annotation.XmlElementDecl;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jztech101 on 6/23/14.
 */

@SuppressWarnings("ALL")
public class Commands extends GenericCommand {
    @CMD
    public Commands() {
        super(GeneralUtils.toArray("commands list cmds coms"), 0, "Takes 0 arguments, returns list of Commands");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, String... args) throws Exception {


        int i = PermUtils.getPermLevel(Bot, user, channel);
        if (i == 9001) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(GeneralRegistry.ControllerListCommands, ", "), isPrivate);
        } else if (i == 20) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(GeneralRegistry.GlobalListCommands, ", "), isPrivate);
        }else if (i == 18) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(GeneralRegistry.ChanFounderListCommands, ", "), isPrivate);
        }else if (i == 15) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(GeneralRegistry.ChanOwnerListCommands, ", "), isPrivate);
        }else if (i == 13) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(GeneralRegistry.ChanOpListCommands, ", "), isPrivate);
        } else if (i == 10) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(GeneralRegistry.ChanOpListCommands, ", "), isPrivate);
        } else if (i == 7) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(GeneralRegistry.ChanHalfOpListCommands, ", "), isPrivate);
        } else if (i == 5) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(GeneralRegistry.TrustedListCommands, ", "), isPrivate);
        } else {
            IRCUtils.SendMessage(user, channel, StringUtils.join(GeneralRegistry.GenericListCommands, ", "), isPrivate);
        }

    }
}

