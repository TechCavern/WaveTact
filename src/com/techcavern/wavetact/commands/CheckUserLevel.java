package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.PermUtils;
import org.pircbotx.hooks.events.MessageEvent;

public class CheckUserLevel extends Command {

    public CheckUserLevel() {
        super("level", 0, "Checks User Level, 0 arguments");
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
