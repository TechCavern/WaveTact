package info.techcavern.wavetact.ircCommands.fun;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Arrays;
import java.util.Random;

@IRCCMD
public class Choose extends IRCCommand {

    public Choose() {
        super(GeneralUtils.toArray("choose chose choice"), 0, "choose [things to choose from]", "Chooses between some things", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Random generator = new Random();
        IRCUtils.sendMessage(user,network, channel,args[generator.nextInt(args.length)],prefix);
    }
}
