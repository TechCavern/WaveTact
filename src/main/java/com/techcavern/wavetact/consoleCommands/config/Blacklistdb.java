/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;

import java.util.ArrayList;
import java.util.List;

import static com.techcavern.wavetactdb.Tables.BLACKLISTS;


/**
 * @author jztech101
 */
@ConCMD
public class Blacklistdb extends ConsoleCommand {


    public Blacklistdb() {
        super(GeneralUtils.toArray("blacklistdb bldb spambldb ircbldb"), "blacklistdb [type] (-)[irc/spam blacklist Url]", "Adds/removes domains from blacklistdb");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) {
        if (args.length > 0) {
            if (args[1].startsWith("-")) {
                Record blacklist = DatabaseUtils.getBlacklist(args[0], args[1].replaceFirst("\\-", "").replaceAll("http://|https://", ""));
                if (blacklist != null) {
                    DatabaseUtils.removeBlacklist(args[0], args[1].replaceFirst("\\-", "").replaceAll("http://|https://", ""));
                    commandIO.getPrintStream().println(args[0] + " blacklist removed");
                } else {
                    commandIO.getPrintStream().println(args[0] + " blacklist does not exist on list");
                }
            } else if (args[1].equalsIgnoreCase("list")) {
                List<String> blacklists = new ArrayList<>();
                for (Record bl : DatabaseUtils.getBlacklists(args[0]))
                    blacklists.add(bl.getValue(BLACKLISTS.URL));
                if (!blacklists.isEmpty()) {
                    commandIO.getPrintStream().println(StringUtils.join(blacklists, ", "));
                } else {
                    commandIO.getPrintStream().println(args[0] + " blacklist is empty");
                }
            } else {
                Record blacklist = DatabaseUtils.getBlacklist(args[0], args[1].replaceFirst("\\-", "").replaceAll("http://|https://", ""));
                if (blacklist == null) {
                    DatabaseUtils.addBlacklist(args[1].replaceFirst("\\-", "").replaceAll("http://|https://", ""), args[0]);
                    commandIO.getPrintStream().println(args[0] + " blacklist added");
                } else {
                    commandIO.getPrintStream().println(args[0] + " blacklist is already listed");
                }
            }
        } else {
            commandIO.getPrintStream().println("Please specify a " + args[0] + " blacklist");
        }
    }
}