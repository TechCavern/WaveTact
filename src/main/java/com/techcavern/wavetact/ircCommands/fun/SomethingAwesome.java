package com.techcavern.wavetact.ircCommands.fun;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@IRCCMD
public class SomethingAwesome extends IRCCommand {

    public SomethingAwesome() {
        super(GeneralUtils.toArray("somethingawesome awesome something"), 1, null, "Something AWESOME!", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (IRCUtils.checkIfCanKick(channel, network, user)) {
            IRCUtils.sendKick(network.getUserBot(), user, network, channel, "┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻");
        } else {
            IRCUtils.sendAction(user, network, channel, "kicks " + user.getNick() + " (┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻)", prefix);
        }
    }
}
