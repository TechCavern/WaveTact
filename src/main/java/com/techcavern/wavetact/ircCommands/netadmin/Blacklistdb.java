/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.netadmin;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

import static com.techcavern.wavetactdb.Tables.BLACKLISTS;


/**
 * @author jztech101
 */
@IRCCMD
public class Blacklistdb extends IRCCommand {


    public Blacklistdb() {
        super(GeneralUtils.toArray("blacklistdb bldb spambldb ircbldb"), 20, "blacklistdb [irc/spam] (-)[irc/spam blacklist Url]", "Adds/removes domains from blacklistdb", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args.length > 0) {
            if (args[1].startsWith("-")) {
                Record blacklist = DatabaseUtils.getBlacklist(args[0], args[1].replaceFirst("\\-", "").replaceAll("http://|https://", ""));
                if (blacklist != null) {
                    DatabaseUtils.removeBlacklist(args[0],args[1].replaceFirst("\\-", "").replaceAll("http://|https://", ""));
                    IRCUtils.sendMessage(user, network, channel, args[0] + " blacklist removed", prefix);
                } else {
                    ErrorUtils.sendError(user, args[0] + " blacklist does not exist on list");
                }
            } else if (args[1].equalsIgnoreCase("list")) {
                List<String> blacklists = new ArrayList<>();
                for(Record bl:DatabaseUtils.getBlacklists(args[0]))
                    blacklists.add(bl.getValue(BLACKLISTS.URL));
                if (!blacklists.isEmpty()) {
                    IRCUtils.sendMessage(user, network, channel, StringUtils.join(blacklists, ", "), prefix);
                } else {
                    ErrorUtils.sendError(user, args[0] + " blacklist is empty");
                }
            } else {
                Record blacklist = DatabaseUtils.getBlacklist(args[0], args[1].replaceFirst("\\-", "").replaceAll("http://|https://", ""));
                if (blacklist == null) {
                    DatabaseUtils.addBlacklist(args[1].replaceFirst("\\-", "").replaceAll("http://|https://", ""), args[0]);
                    IRCUtils.sendMessage(user, network, channel, args[0] + " blacklist added", prefix);
                } else {
                    ErrorUtils.sendError(user, args[0] + " blacklist is already listed");
                }
            }
        } else {
            ErrorUtils.sendError(user, "Please specify a "+args[0]+" blacklist");
        }
    }
}