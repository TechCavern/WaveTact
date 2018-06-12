/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.techcavern.wavetact.ircCommands.fun;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.RandomUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@IRCCMD
public class Attack extends IRCCommand {

    public Attack() {
        super(GeneralUtils.toArray("attack shoot slap at"), 0, "attacks [something]", "attacks a user", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String Something = GeneralUtils.buildMessage(0, args.length, args);
        if (Something.toLowerCase().equalsIgnoreCase(network.getUserBot().getNick())) {
            Something = user.getNick();
        }
        int randomint = Registry.randNum.nextInt(Registry.attacks.size());
        IRCUtils.sendAction(user, network, channel, ((String) Registry.attacks.toArray()[randomint]).replace("$*", IRCUtils.noPing(Something)), prefix);
    }
}
