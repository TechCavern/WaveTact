package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;

public class CheckUserLevel extends GenericCommand {

    @CMD
    @GenCMD
    public CheckUserLevel() {
        super(GeneralUtils.toArray("checkuserlevel level checklevel"), 0, "checkuserlevel (user)");
    }

    @Override
    public void onCommand(User user, PircBotZ Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String nick = null;
        if (args.length < 1) {
            nick=user.getNick();
        } else {
            nick = args[0];
            UserPermLevel = PermUtils.getPermLevel(Bot, GetUtils.getUserByNick(Bot,args[0]), channel);
        }
        if (UserPermLevel >= 9001) {
            IRCUtils.SendMessage(user, channel, nick + " is my Master!", isPrivate);
        } else if (UserPermLevel >= 20) {
            IRCUtils.SendMessage(user, channel, nick + " is a Network Administrator!", isPrivate);
        } else if (UserPermLevel >= 18) {
            IRCUtils.SendMessage(user, channel, nick + " is a Channel Founder!", isPrivate);
        } else if (UserPermLevel >= 15) {
            IRCUtils.SendMessage(user, channel, nick + " is a Channel Owner!", isPrivate);
        } else if (UserPermLevel >= 13) {
            IRCUtils.SendMessage(user, channel, nick + " is a Channel Admin!", isPrivate);
        } else if (UserPermLevel >= 10) {
            IRCUtils.SendMessage(user, channel, nick + " is a Channel Operator!", isPrivate);
        } else if (UserPermLevel >= 7) {
            IRCUtils.SendMessage(user, channel, nick + " is a Channel Half-Operator", isPrivate);
        } else if (UserPermLevel >= 5) {
            IRCUtils.SendMessage(user, channel, nick + " is a Trusted User!", isPrivate);
        } else {
            IRCUtils.SendMessage(user, channel, nick + " is a Regular User!", isPrivate);
        }


    }
}
