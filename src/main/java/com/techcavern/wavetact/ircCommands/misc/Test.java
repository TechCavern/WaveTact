package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

//@IRCCMD
public class Test extends IRCCommand {

    public Test() {
        super(GeneralUtils.toArray("test dev"), 0, "test", "Test Command (This should not show up in a production environment. If it does, report it", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        channel.send().message("^dev");
        for (PircBotX bot : Registry.networkName.keySet()) {
            if (bot != network) {
                channel.send().kick(bot.getUserBot());
            }
        }
    }
}
