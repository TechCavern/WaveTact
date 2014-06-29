package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralRegistry;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jztech101 on 6/23/14.
 */

@SuppressWarnings("ALL")
public class Commands extends Command {
    public Commands() {
        super("Commands", 0, "Takes 0 arguments, returns list of Commands");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        List<String> commands = new ArrayList<String>();
        for (Command command : GeneralRegistry.Commands) {
            commands.add(command.getCommandID());
        }
        String commandstring = StringUtils.join(commands, ", ");
        event.respond(commandstring);
    }
}

