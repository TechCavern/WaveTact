/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class Kick extends Command {
    @CMD
    public Kick() {
        super("kick", 10, "kick [user]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {

        if (event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OP) && !event.getChannel().isOwner(IRCUtils.getUserByNick(event.getChannel(), args[0])) && !event.getChannel().isSuperOp(IRCUtils.getUserByNick(event.getChannel(), args[0]))) {
            if (args.length > 1) {
                event.getChannel().send().kick(IRCUtils.getUserByNick(event.getChannel(), args[0]), args[1]);
            } else {
                event.getChannel().send().kick(IRCUtils.getUserByNick(event.getChannel(), args[0]));

            }
        } else if (event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OWNER)) {
            if (args.length > 1) {
                event.getChannel().send().kick(IRCUtils.getUserByNick(event.getChannel(), args[0]), args[1]);
            } else {
                event.getChannel().send().kick(IRCUtils.getUserByNick(event.getChannel(), args[0]));

            }
        } else {
            event.getChannel().send().message("Error: I must be ownered in the channel to kick someone that is protected or ownered");
        }
    }

}
