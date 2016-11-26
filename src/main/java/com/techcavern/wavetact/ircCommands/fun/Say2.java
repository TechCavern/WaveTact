/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.fun;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@IRCCMD
public class Say2 extends IRCCommand {

    public Say2() {
        super(GeneralUtils.toArray("say2 msg2 s2 a2 act2 do2 prism2 echo2"), 7, "say2 [something]", "Makes the bot say something", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
            if (command.equalsIgnoreCase("act2") || command.equalsIgnoreCase("do2") || command.equalsIgnoreCase("a2")) {
                    IRCUtils.sendAction(user, network, channel, StringUtils.join(args, " "), prefix);
            } else if (command.equalsIgnoreCase("prism2")) {
                    IRCUtils.sendMessage(user, network, channel, GeneralUtils.prism(StringUtils.join(args, " ")), prefix);
            } else {
                    IRCUtils.sendMessage(user, network, channel, StringUtils.join(args, " "), prefix);
            }
    }
}



