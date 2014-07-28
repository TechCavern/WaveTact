package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;


public class Shutdown extends GenericCommand {
    @CMD
    @ConCMD

    public Shutdown() {
        super(GeneralUtils.toArray("shutdown down"), 9001, "Shutdown");
    }

    @Override
    public void onCommand(User user, PircBotZ Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        GeneralRegistry.WaveTact.stop();
        System.exit(0);

    }
}

