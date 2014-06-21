/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.utils.Command;
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author jztech101
 */
public class SomethingAwesome extends Command {

    public SomethingAwesome() {
        super("somethingawesome", 0);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        if (event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OP) && event.getChannel().isOwner(event.getUser()) == false && event.getChannel().isSuperOp(event.getUser()) == false) {
            event.getChannel().send().kick(event.getUser(), "http://bit.ly/1c9vo1S");
        } else if (event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OWNER)) {
            event.getChannel().send().kick(event.getUser(), "http://bit.ly/1c9vo1S");
        } else {
            event.getChannel().send().message("http://bit.ly/1c9vo1S");

        }
    }
}
