/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class Mode extends GenericCommand {
    @CMD
    public Mode() {
        super(GeneralUtils.toArray("mode mo"), 0, "mode (channel) [modes to set]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        if(!args[0].contains("#")){
            if(PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel()) >= 9){
                if(event.getChannel().isOp(event.getBot().getUserBot()) || event.getChannel().isSuperOp(event.getBot().getUserBot()) || event.getChannel().isOwner(event.getBot().getUserBot())) {
                    event.getChannel().send().setMode(args[0]);
                }else{
                    event.getChannel().send().message("Error I must be at least op to perform the operation requested.");
                }
            }else{
                event.getChannel().send().message("Permission Denied");
            }
        }else{
            if(PermUtils.getPermLevel(event.getBot(), event.getUser(), GetUtils.getChannelbyName(event.getBot(), args[0])) >= 9){
                if(GetUtils.getChannelbyName(event.getBot(), args[0]).isOp(event.getBot().getUserBot()) || GetUtils.getChannelbyName(event.getBot(), args[0]).isSuperOp(event.getBot().getUserBot()) || GetUtils.getChannelbyName(event.getBot(), args[0]).isOwner(event.getBot().getUserBot())) {
                    GetUtils.getChannelbyName(event.getBot(), args[0]).send().setMode(args[0]);
                }else{
                    event.respond("Error I must be at least op in the channel to perform the operation requested.");
                }
            }else{
                event.respond("Permission Denied");
            }
        }
    }
}
