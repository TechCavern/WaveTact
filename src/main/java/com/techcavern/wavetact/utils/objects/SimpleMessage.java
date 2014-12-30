/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils.objects;

import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
public class SimpleMessage extends IRCCommand {

    private final String message;
    private boolean locked;


    public SimpleMessage(String inputString, int permLevel, String message, boolean locked) {
        super(GeneralUtils.toArray(inputString), permLevel, null, "A basic command", false);
        this.message = message;
        this.locked = locked;
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String actionmessage = this.message;
        String[] message = StringUtils.split(this.message, " ");
        int i = 0;
        for (String g : message) {
            if (g.startsWith("$") && !g.contains("*")) {
                actionmessage = actionmessage.replace(g, args[Integer.parseInt(g.replace("$", "")) - 1]);
                if (Integer.parseInt(g.replace("$", "")) > i) {
                    i++;
                }
            }
        }
        actionmessage = actionmessage.replace("$*", GeneralUtils.buildMessage(i, args.length, args));
        String responseprefix = GetUtils.getCommandChar(network);
        if (actionmessage.startsWith(responseprefix)) {
            actionmessage = actionmessage.replace(responseprefix, "");
        }
        IRCUtils.sendMessage(user,network, channel, actionmessage, prefix);
    }


    public String getMessage() {
        return this.message;
    }


    @Override
    public boolean getLockedStatus() {
        return locked;
    }

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }
}
