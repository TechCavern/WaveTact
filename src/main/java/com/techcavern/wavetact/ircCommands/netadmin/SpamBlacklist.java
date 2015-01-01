/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.netadmin;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@IRCCMD
public class SpamBlacklist extends IRCCommand {

    public SpamBlacklist() {
        super(GeneralUtils.toArray("spamblacklist spambldb"), 20, "spamblacklist (-)[spam blacklist url]", "Adds/Removes Domains from Spam DNS Blacklists", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        GeneralUtils.modifyBlacklist(user,network,channel, args, prefix, "spam");
    }
}
