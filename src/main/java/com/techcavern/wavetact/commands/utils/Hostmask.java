package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import org.pircbotx.hooks.events.MessageEvent;


public class Hostmask extends GenericCommand {
    @CMD
    public Hostmask() {
        super(GeneralUtils.toArray("hostmask host mask"), 0, "Hostmask [nick]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.getChannel().send().message(GetUtils.getUserByNick(event.getChannel(), args[0]).getHostmask());

    }
}
