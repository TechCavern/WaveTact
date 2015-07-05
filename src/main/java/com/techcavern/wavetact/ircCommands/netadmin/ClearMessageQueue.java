package com.techcavern.wavetact.ircCommands.netadmin;


import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.objects.NetProperty;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class ClearMessageQueue extends IRCCommand {

    public ClearMessageQueue() {
        super(GeneralUtils.toArray("clearmsgqueue clearmessagequeue"), 20, "clearmsgqueue", "clears the messages queue", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Registry.MessageQueue.stream().filter(msg -> msg.getNetwork().equals(network)).forEach(msg -> {
            Registry.MessageQueue.remove(msg);

        });
        IRCUtils.sendMessage(user, network, channel, "Message Queue Cleared", prefix);

    }
}

