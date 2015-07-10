package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Tell extends IRCCommand {

    public Tell() {
        super(GeneralUtils.toArray("tell"), 1, "tell [user] [message]", "Tells a user a message the next time they speak", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Record relaybotsplit = DatabaseUtils.getChannelUserProperty(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), PermUtils.authUser(network, user.getNick()), "relaybotsplit");
        if (relaybotsplit == null) {
            String sender = PermUtils.authUser(network, user.getNick());
            String recipent = PermUtils.authUser(network, args[0]);
            if (recipent == null) {
                ErrorUtils.sendError(user, "Recipient must be identified");
                return;
            }
            DatabaseUtils.addTellMessage(IRCUtils.getNetworkNameByNetwork(network), sender, recipent, GeneralUtils.buildMessage(1, args.length, args));
            IRCUtils.sendMessage(user, network, channel, "Latent Message Sent", prefix);
        }
    }

}
