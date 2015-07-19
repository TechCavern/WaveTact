package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class PermLevel extends IRCCommand {

    public PermLevel() {
        super(GeneralUtils.toArray("permlevel pl perm level"), 0, "permlevel (user)", "Checks Perm Level of the specified user or yourself if unspecified", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String userObject;
        Channel chan;
        if (args.length > 0 && network.getConfiguration().getChannelPrefixes().contains(String.valueOf(args[0].charAt(0)))) {
            chan = IRCUtils.getChannelbyName(network, args[0]);
            args = ArrayUtils.remove(args, 0);
        } else {
            chan = channel;
        }
        if (args.length < 1) {
            userObject = user.getNick();
        } else {
            userObject = args[0];
        }
        userPermLevel = PermUtils.getPermLevel(network, userObject, chan);
        if (userObject == null) {
            IRCUtils.sendError(user, network, channel, "User does not exist", prefix);
        } else if (userPermLevel >= 20) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Network Administrator!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 18) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Channel Administrator!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 15) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Senior Channel Operator!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 13) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Protected Channel Operator!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 10) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Channel Operator!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 7) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Channel Half-Operator" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 5) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Channel Trusted User!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 1) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Registered User!" + " (" + userPermLevel + ")", prefix);
        } else if (userPermLevel >= 0) {
            IRCUtils.sendMessage(user, network, channel, userObject + " is a Regular User!" + " (" + userPermLevel + ")", prefix);
        } else {
            IRCUtils.sendMessage(user, network, channel, userObject + " is an Ignored User!" + " (" + userPermLevel + ")", prefix);
        }


    }
}
