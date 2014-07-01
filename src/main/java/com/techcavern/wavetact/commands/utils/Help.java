package com.techcavern.wavetact.commands.utils;

import com.sun.deploy.util.StringUtils;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Arrays;

public class Help extends Command {
    @CMD
    public Help() {
        super(GeneralUtils.toArray("help h halp"), 0, "help (command)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("permissions")) {
                event.respond("0 = Everyone, 5 = Voiced & Half-opped, 10 = Opped & Protected, 15 = Ownered, 9001 = Controller ");
            } else {
                event.respond("aliases: " + StringUtils.join(Arrays.asList(IRCUtils.getCommand(args[0]).getCommandID()), " "));
                event.respond(IRCUtils.getCommand(args[0]).getDesc());
            }
        } else {
            event.respond("help [command] - Generally a + before something means editing it, and a - means removing it. None means adding it. - Time is in [time](s/m/h/d) format, IRCd is specified with c for Charybdis, u for Unreal, and i for inspircd.\n");


        }
    }
}
