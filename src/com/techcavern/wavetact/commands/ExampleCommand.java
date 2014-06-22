package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.utils.AbstractCommand;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * For Future Reference
 */
@SuppressWarnings("ALL")
public class ExampleCommand extends AbstractCommand {
    public ExampleCommand() {
        super("blah", 10);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.getChannel().send().message("hi");
    }
}