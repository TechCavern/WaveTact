package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
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
        User u;
        if(args.length < 1){
            u = event.getUser();
        }else{
            u = IRCUtils.getUserByNick(event.getChannel(),args[0]);
        }

        int i = PermUtils.getPermLevel(event.getBot(), u, event.getChannel());
        if (i == 9001) {
            event.respond(u.getNick()+ " are my Master!");
        } else if (i == 15) {
            event.respond(u.getNick()+ " are a Channel Owner!");

        } else if (i == 10) {
            event.respond(u.getNick()+ " are a Channel Operator!");
        } else if (i == 5) {
            event.respond(u.getNick()+ " are a Trusted User!");
        } else {
            event.respond(u.getNick()+ " are a Regular User!");
        }
    }
}
