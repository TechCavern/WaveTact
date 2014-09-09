package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class CheckUserLevel extends GenericCommand {

    public CheckUserLevel() {
        super(GeneralUtils.toArray("checkuserlevel level checklevel"), 0, "checkuserlevel (user)", "Checks User Level of the specified user or yourself if unspecified");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String userObject;
        if (args.length > 1 && args[0].startsWith("#")) {
            channel = GetUtils.getChannelbyName(Bot, args[0]);
            args = ArrayUtils.remove(args, 0);
        }
        if (args.length < 1) {
            userObject = user.getNick();
        } else {
            userObject = args[0];
            UserPermLevel = PermUtils.getPermLevel(Bot, userObject, channel);
        }

        if (userObject == null) {
            IRCUtils.sendError(user, "User does not exist");
        } else if (UserPermLevel >= 9001) {
            IRCUtils.sendMessage(user, channel, userObject + " is a Bot Controller!" + " (" + UserPermLevel+ ")", isPrivate);
        } else if (UserPermLevel >= 20) {
            IRCUtils.sendMessage(user, channel, userObject + " is a Network Administrator!" + " (" + UserPermLevel+ ")", isPrivate);
        } else if (UserPermLevel >= 18) {
            IRCUtils.sendMessage(user, channel, userObject + " is a Channel Founder!" + " (" + UserPermLevel+ ")", isPrivate);
        } else if (UserPermLevel >= 15) {
            IRCUtils.sendMessage(user, channel, userObject + " is a Channel Owner!" + " (" + UserPermLevel+ ")", isPrivate);
        } else if (UserPermLevel >= 13) {
            IRCUtils.sendMessage(user, channel, userObject + " is a Channel Admin!" + " (" + UserPermLevel+ ")", isPrivate);
        } else if (UserPermLevel >= 10) {
            IRCUtils.sendMessage(user, channel, userObject + " is a Channel Operator!" + " (" + UserPermLevel+ ")", isPrivate);
        } else if (UserPermLevel >= 7) {
            IRCUtils.sendMessage(user, channel, userObject + " is a Channel Half-Operator" + " (" + UserPermLevel+ ")", isPrivate);
        } else if (UserPermLevel >= 5 && !isPrivate) {
            IRCUtils.sendMessage(user, channel, userObject + " is a Channel Trusted User!" + " (" + UserPermLevel+ ")", isPrivate);
        } else {
            IRCUtils.sendMessage(user, channel, userObject + " is a Regular User!" + " (" + UserPermLevel+ ")", isPrivate);
        }


    }
}
