/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.objects.objectUtils.ControllerUtils;
import com.techcavern.wavetact.utils.objects.objectUtils.GlobalUtils;
import org.pircbotx.hooks.events.MessageEvent;


/**
 * @author jztech101
 */
public class Globals extends Command {
    @CMD
    public Globals() {
        super(GeneralUtils.toArray("globals gl"), 9001, "global (-)[user]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
        if (args[1].startsWith("-")) {
            GeneralRegistry.Globals.remove(args[1].replace("-", ""));
            GlobalUtils.saveGlobals();
        } else {
            GeneralRegistry.Globals.add(args[1]);
            GlobalUtils.saveGlobals();
        }
    }
}
