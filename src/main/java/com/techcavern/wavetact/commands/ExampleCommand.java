package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * For Future Reference
 */
@SuppressWarnings("ALL")
public class ExampleCommand extends GenericCommand {
    @CMD
    public ExampleCommand() {
        super(new String[]{"blah"}, 0, "description");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.respond("hi");
    }
}
