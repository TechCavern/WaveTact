package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.LoadUtils;
import org.pircbotx.hooks.events.MessageEvent;


public class Hostmask extends Command {
    @CMD
    public Hostmask() {
        super(GeneralUtils.toArray("hostmask host mask"), 0, "Hostmask [nick]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.getChannel().send().message(GetUtils.getUserByNick(event.getChannel(), args[0]).getHostmask());

    }
}
