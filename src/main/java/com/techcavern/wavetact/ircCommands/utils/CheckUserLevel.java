package com.techcavern.wavetact.ircCommands.utils;

import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
@GenCMD
public class CheckUserLevel extends IRCCommand {

    public CheckUserLevel() {
        super(GeneralUtils.toArray("checkuserlevel level checklevel"), 0, "checkuserlevel (user)", "Checks User Level of the specified user or yourself if unspecified", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String userObject;
        Channel chan;
        if (args.length > 1 && network.getServerInfo().getChannelTypes().contains(String.valueOf(args[0].charAt(0)))) {
            chan = IRCUtils.getChannelbyName(network, args[0]);
            args = ArrayUtils.remove(args, 0);
        } else {
            chan = channel;
        }
        if (args.length < 1) {
            userObject = user.getNick();
        } else {
            userObject = args[0];
            userPermLevel = PermUtils.getPermLevel(network, userObject, chan);
        }
        if (userObject == null) {
            ErrorUtils.sendError(user, "User does not exist");
        } else if (userPermLevel >= 9001) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a bot controller!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 20) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a network administrator!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 18) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a channel administrator!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 15) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a channel ownered operator!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 13) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a channel protected operator!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 10) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a channel operator!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 7) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a channel half operator" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 5 && !isPrivate) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a channel trusted user!" + " (" + userPermLevel + ")", prefix);
        } else {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a regular user!" + " (" + userPermLevel + ")", prefix);
        }


    }
}
