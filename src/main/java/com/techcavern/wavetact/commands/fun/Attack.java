/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.fun;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.Command;
import org.apache.commons.lang3.RandomUtils;
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.concurrent.TimeUnit;

/**
 * @author jztech101
 */
public class Attack extends Command {

    @CMD
    public Attack() {
        super(GeneralUtils.toArray("attack shoot a s"), 0, "attacks [something]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        String Something = GeneralUtils.buildMessage(0, args.length, args);
        int randomint = RandomUtils.nextInt(0 , 6);
        switch(randomint){
            case 1:
                event.getChannel().send().action("sends a 53 inch monitor flying at " + Something);
                break;
            case 2:
                event.getChannel().send().action("shoots a rocket at " + Something);
                break;
            case 3:
                event.getChannel().send().action("punches "+Something+" right in the nuts");
                break;
            case 4:
                event.getChannel().send().action("places a bomb near "+Something+" set for 5 seconds");
                TimeUnit.SECONDS.sleep(5);
                event.getChannel().send().message("BANG");
                break;
            case 5:
                event.getChannel().send().action("drops a 2000 pound object on "+Something);
                break;
        }

    }
}
