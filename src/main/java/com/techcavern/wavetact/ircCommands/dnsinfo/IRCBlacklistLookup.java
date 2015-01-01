/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.dnsinfo;

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
public class IRCBlacklistLookup extends IRCCommand {

    public IRCBlacklistLookup() {
        super(GeneralUtils.toArray("ircblacklistlookup ibl ircbl"), 5, "ircblacklistlookup [ip/domain/user]", "Looks up a domain or ip to see if its in a drone blacklist", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        GeneralUtils.checkBlacklist(user, args, network, channel, prefix, "irc");

    }
}

