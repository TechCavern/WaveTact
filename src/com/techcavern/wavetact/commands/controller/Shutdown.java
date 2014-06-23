package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.objects.Command;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by jztech101 on 6/23/14.
 */
public class Shutdown extends Command {
    public Shutdown() {
        super("Shutdown", 9001);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
    }
}
