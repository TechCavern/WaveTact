package com.techcavern.wavetact.ircCommands.netadmin;


import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.objects.NetString;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class IRCRaw extends IRCCommand {

    public IRCRaw() {
        super(GeneralUtils.toArray("ircraw raw"), 20, "raw [to be sent to server]", "Sends a raw msg to the server", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args[0].equalsIgnoreCase("kick") && args[2].equalsIgnoreCase(network.getNick())) {
            args[2] = user.getNick();
        }

        Registry.MessageQueue.add(new NetString(GeneralUtils.buildMessage(0, args.length, args), network));
    }
}

