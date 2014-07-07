/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.fun;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class SomethingAwesome extends GenericCommand {

    @CMD
    public SomethingAwesome() {
        super(GeneralUtils.toArray("somethingawesome sa awesome something"), 0, "somethingawesome");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        if (event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OP) && !event.getChannel().isOwner(event.getUser()) && !event.getChannel().isSuperOp(event.getUser())) {
            event.getChannel().send().kick(event.getUser(), "http://bit.ly/1c9vo1S");
        } else if (event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OWNER)) {
            event.getChannel().send().kick(event.getUser(), "http://bit.ly/1c9vo1S");
        } else {
            event.respond("http://bit.ly/1c9vo1S");

        }
    }
}
