/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@IRCCMD
public class Title extends IRCCommand {

    public Title() {
        super(GeneralUtils.toArray("title ti"), 1, "title [url]", "gets title", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!args[0].startsWith("http://") && !args[0].startsWith("https://")) {
            args[0] = "http://" + args[0];
        }
        Document doc = Jsoup.connect(args[0]).userAgent(Registry.userAgent).get();
        IRCUtils.sendMessage(user, network, channel, doc.title(), prefix);
    }
}
