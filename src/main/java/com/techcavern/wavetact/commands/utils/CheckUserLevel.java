package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class CheckUserLevel extends GenericCommand {

    public CheckUserLevel() {
        super(GeneralUtils.toArray("checkuserlevel level checklevel"), 0, "checkuserlevel (user)", "Checks User Level of the specified user or yourself if unspecified", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String userObject;
        if (args.length > 1 && args[0].startsWith("#")) {
            channel = GetUtils.getChannelbyName(network, args[0]);
            args = ArrayUtils.remove(args, 0);
        }
        if (args.length < 1) {
            userObject = user.getNick();
        } else {
            userObject = args[0];
            userPermLevel = PermUtils.getPermLevel(network, userObject, channel);
        }
        if (userObject == null) {
            ErrorUtils.sendError(user, "User does not exist");
        } else if (userPermLevel >= 9001) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Bot Controller!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 20) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Network Administrator!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 18) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Channel Founder!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 15) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Channel Owner!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 13) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Channel Admin!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 10) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Channel Operator!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 7) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Channel Half-Operator" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 5 && !isPrivate) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Channel Trusted User!" + " (" + userPermLevel + ")", prefix);
        } else {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Regular User!" + " (" + userPermLevel + ")", prefix);
        }


    }
}
