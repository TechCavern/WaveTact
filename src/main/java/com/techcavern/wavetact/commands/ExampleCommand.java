package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralRegistry;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * For Future Reference
 */
@SuppressWarnings("ALL")
public class ExampleCommand extends Command {
    public ExampleCommand() {
        super("blah", 0, "description");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
          event.respond("hi");
    }
}