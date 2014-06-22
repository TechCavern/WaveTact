package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.objects.Command;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.PermUtils;

public class CheckUserLevel extends Command {

    public CheckUserLevel() {
        super("level", 10);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        int i = PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel());
        if (i == 9001) {
            event.getChannel().send().message("You are my Master!");
        } else if (i == 15) {
            event.getChannel().send().message("You are a Channel Owner!");

        } else if (i == 10) {
            event.getChannel().send().message("You are a Channel Operator!");
        } else if (i == 5) {
            event.getChannel().send().message("You are a Trusted User!");
        } else {
            event.getChannel().send().message("You are a Regular User!");
        }
    }
}
