package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.PermUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class CheckUserLevel extends GenericCommand {

    @CMD
    public CheckUserLevel() {
        super(GeneralUtils.toArray("checkuserlevel level checklevel"), 0, "Checks User Level, 0 arguments");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel,boolean isPrivate, String... args) throws Exception {
        User userObject;
        if (args.length < 1) {
            userObject = user;
        } else {
            userObject = GetUtils.getUserByNick(channel, args[0]);
        }

        int i = PermUtils.getPermLevel(Bot, userObject, channel);
        if (i == 9001) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is my Master!", isPrivate);
        } else if (i == 20) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Network Administrator!", isPrivate);
        }else if (i == 18) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Channel Founder!", isPrivate);
        }else if (i == 15) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Channel Owner!", isPrivate);
        }else if (i == 13) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Channel Admin!", isPrivate);
        } else if (i == 10) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Channel Operator!", isPrivate);
        } else if (i == 7) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Channel Half-Operator", isPrivate);
        } else if (i == 5) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Trusted User!", isPrivate);
        } else {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Regular User!", isPrivate);
        }

    }
}
