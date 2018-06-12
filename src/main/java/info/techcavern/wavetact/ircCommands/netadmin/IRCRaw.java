package info.techcavern.wavetact.ircCommands.netadmin;


import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringEscapeUtils;
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

        Registry.messageQueue.get(network).add(StringEscapeUtils.unescapeJava(GeneralUtils.buildMessage(0, args.length, args)));
    }
}

