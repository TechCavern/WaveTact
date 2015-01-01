/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.xbill.DNS.*;
import org.xbill.DNS.Record;
import static com.techcavern.wavetactdb.Tables.*;

/**
 * @author jztech101
 */
@IRCCMD
public class SpamBlacklistLookup extends IRCCommand {

    public SpamBlacklistLookup() {
        super(GeneralUtils.toArray("spamblacklistlookup sbl spambl"), 5, "spamblacklistlookup [ip/domain/user]", "Looks up a domain or IP to see if its in a spam blacklist", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        GeneralUtils.checkBlacklist(user, args, network, channel, prefix, "spam");
    }
}

