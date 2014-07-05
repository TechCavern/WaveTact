package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.PermUtils;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class CheckUserLevel extends Command {

    @CMD
    public CheckUserLevel() {
        super(GeneralUtils.toArray("checkuserlevel level checklevel"), 0, "Checks User Level, 0 arguments");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        User userObject;
        if (args.length < 1) {
            userObject = event.getUser();
        } else {
            userObject = GetUtils.getUserByNick(event.getChannel(), args[0]);
        }

        int i = PermUtils.getPermLevel(event.getBot(), userObject, event.getChannel());
        if (i == 9001) {
            event.respond(userObject.getNick() + " is my Master!");
        } else if (i == 15) {
            event.respond(userObject.getNick() + " is a Channel Owner!");
        } else if (i == 10) {
            event.respond(userObject.getNick() + " is a Channel Operator!");
        } else if (i == 7) {
            event.respond(userObject.getNick() + " is a Channel Half-Operator");
        } else if (i == 5) {
            event.respond(userObject.getNick() + " is a Trusted User!");
        } else {
            event.respond(userObject.getNick() + " is a Regular User!");
        }
    }
}
