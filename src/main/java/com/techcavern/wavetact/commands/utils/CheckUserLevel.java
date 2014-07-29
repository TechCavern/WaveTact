package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public class CheckUserLevel extends GenericCommand {

    @CMD
    @GenCMD
    public CheckUserLevel() {
        super(GeneralUtils.toArray("checkuserlevel level checklevel"), 0, "Checks User Level, 0 arguments");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        User userObject;
        if (args.length < 1) {
            userObject = user;
        } else {
            userObject = GetUtils.getUserByNick(Bot, args[0]);
            UserPermLevel = PermUtils.getPermLevel(Bot, userObject, channel);
        }
        if (UserPermLevel >= 9001) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is my Master!", isPrivate);
        } else if (UserPermLevel >= 20) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Network Administrator!", isPrivate);
        } else if (UserPermLevel >= 18) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Channel Founder!", isPrivate);
        } else if (UserPermLevel >= 15) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Channel Owner!", isPrivate);
        } else if (UserPermLevel >= 13) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Channel Admin!", isPrivate);
        } else if (UserPermLevel >= 10) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Channel Operator!", isPrivate);
        } else if (UserPermLevel >= 7) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Channel Half-Operator", isPrivate);
        } else if (UserPermLevel >= 5) {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Trusted User!", isPrivate);
        } else {
            IRCUtils.SendMessage(user, channel, userObject.getNick() + " is a Regular User!", isPrivate);
        }


    }
}
