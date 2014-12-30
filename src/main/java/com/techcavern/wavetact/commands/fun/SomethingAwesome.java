package com.techcavern.wavetact.commands.fun;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.UserLevel;

/**
 * @author jztech101
 */
@CMD
@GenCMD
public class SomethingAwesome extends IRCCommand {

    public SomethingAwesome() {
        super(GeneralUtils.toArray("somethingawesome awesome something"), 0, null, "Something AWESOME!", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (channel != null && channel.getUserLevels(network.getUserBot()).contains(UserLevel.OP) && !channel.isOwner(user) && !channel.isSuperOp(user)) {
            channel.send().kick(user, "http://bit.ly/1c9vo1S");
        } else if (channel != null && channel.getUserLevels(network.getUserBot()).contains(UserLevel.OWNER)) {
            channel.send().kick(user, "http://bit.ly/1c9vo1S");
        } else {
            IRCUtils.sendAction(user, network, channel, "kicks " + user.getNick() + "(http://bit.ly/1c9vo1S)", prefix);

        }
    }
}
