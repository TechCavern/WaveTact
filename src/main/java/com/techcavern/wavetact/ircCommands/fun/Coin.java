package com.techcavern.wavetact.ircCommands.fun;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Random;

@IRCCMD
public class Coin extends IRCCommand {

    public Coin() {
        super(GeneralUtils.toArray("coin"), 0, "coin", "Flips a coin", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int x = Registry.randNum.nextInt(5);
        if (x == 0 || x == 1) {
            IRCUtils.sendMessage(user, network, channel, "The coin lands on heads!", prefix);
        } else if (x == 2 || x == 3) {
            IRCUtils.sendMessage(user, network, channel, "The coin lands on tails!", prefix);
        } else {
            IRCUtils.sendMessage(user, network, channel, "The coin lands on its side!", prefix);
        }
    }
}
