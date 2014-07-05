package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.objects.CommandLine;
import com.techcavern.wavetact.utils.objects.PermUser;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by jztech101 on 7/5/14.
 */
public class PermLevel extends Command {
    @CMDLine
    public PermLevel() {
        super(GeneralUtils.toArray("permlevel pl"), 9001, "permlevel (+)(-)[user/hostmask] [permlevel]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String[] args) {
        if(args[0].contains("!") && args[0].contains("@")){
            if(args[0].startsWith("-")){
                GeneralRegistry.PermUserHostmasks.remove(GetUtils.getPermUserHostmaskbyHostmask(args[0].replaceFirst("-", "")));
            }else if(args[0].startsWith("+")){
                GetUtils.getPermUserHostmaskbyHostmask(args[0].replaceFirst("\\+", "")).setPermLevel(Integer.parseInt(args[1]));
            }else{
                PermUtils.createPermHostmask(args[0], Integer.parseInt(args[1]));
            }
        }else{
            if(args[1].startsWith("-")){
                GeneralRegistry.PermUsers.remove(GetUtils.getPermUserbyNick(args[0].replaceFirst("-", "")));
            }else if(args[1].startsWith("+")){
                GetUtils.getPermUserbyNick(args[0].replaceFirst("\\+", "")).setPermLevel(Integer.parseInt(args[1]));
            }else{
                GeneralRegistry.PermUsers.add(new PermUser(args[0], Integer.parseInt(args[1])));
            }
        }
    }
}
