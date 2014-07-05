package com.techcavern.wavetact.commandline.perms;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.*;
import com.techcavern.wavetact.utils.objects.objectUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.objects.objectUtils.SimpleMessageUtils;

/**
 * Created by jztech101 on 7/5/14.
 */
public class PermLevelC extends CommandLine {
    @CMDLine
    public PermLevelC() {
        super(GeneralUtils.toArray("permlevel pl"), "permlevel (+)(-)[user/hostmask] [permlevel]", false);
    }

    @Override
    public void doAction(String[] args) {
        if(args[1].contains("!") && args[1].contains("@")){
            if(args[1].startsWith("-")){
                GeneralRegistry.PermUserHostmasks.remove(GetUtils.getPermUserHostmaskbyHostmask(args[1].replaceFirst("-", "")));
            }else if(args[1].startsWith("+")){
                GetUtils.getPermUserHostmaskbyHostmask(args[1].replaceFirst("\\+", "")).setPermLevel(Integer.parseInt(args[2]));
            }else{
                PermUtils.createPermHostmask(args[1], Integer.parseInt(args[2]));
            }
        }else{
            if(args[1].startsWith("-")){
                GeneralRegistry.PermUsers.remove(GetUtils.getPermUserbyNick(args[1].replaceFirst("-", "")));
            }else if(args[1].startsWith("+")){
                GetUtils.getPermUserbyNick(args[1].replaceFirst("\\+", "")).setPermLevel(Integer.parseInt(args[2]));
            }else{
                GeneralRegistry.PermUsers.add(new PermUser(args[1], Integer.parseInt(args[2])));
            }
        }
    }
}
