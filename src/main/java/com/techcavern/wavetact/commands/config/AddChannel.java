package com.techcavern.wavetact.commands.config;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.file.Configuration;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@ConCMD
public class AddChannel extends GenericCommand {

    public AddChannel() {
        super(GeneralUtils.toArray("addchan"), 1, "addchannel [channel]", "Add a channel to this server's configuration file");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String addChannel = args[0];
        String currentServer = Bot.getConfiguration().getServerHostname();
        System.out.println("BLARGH");
        IRCUtils.sendMessage(user, channel, String.valueOf(GeneralRegistry.configs.size()), false);
        GeneralRegistry.configs.forEach((String key, Configuration val) -> {
            IRCUtils.sendMessage(user, channel, currentServer + " " + key, false);
            if (currentServer.equals(key)) {
                //IRCUtils.sendMessage(user, channel, "lel", false);
            }
        });
        String channels = GeneralRegistry.configs.get(currentServer).getString("channels");
        IRCUtils.sendMessage(user, channel, channels, false);
    }
}
