/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class Say extends Command {

    @CMD
    public Say() {
        super(GeneralUtils.toArray("say msg"), 5, "say [something]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.getChannel().send().message(StringUtils.join(args, " "));

    }
}
