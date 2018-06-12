/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.techcavern.wavetact.ircCommands.dnsinfo;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.xbill.DNS.*;

import java.util.Random;

/**
 * @author jztech101
 */
@IRCCMD
public class ISup extends IRCCommand {

    public ISup() {
        super(GeneralUtils.toArray("isup isupme"), 1, "isup [domain]", "Looks up a domain on isup.me to see if it is up", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!args[0].startsWith("http://") && !args[0].startsWith("https://")) {
            args[0] = "http://" + args[0];
        }
        Document doc = Jsoup.connect("http://www.isup.me/" + args[0]).userAgent(Registry.USER_AGENT).get();
        String c = doc.select("#container").text();
        if(c.contains("not just you")){
            c = "It's not just you! " + args[0] + " looks down from here too. (Please note that isup.me - the service we use - lacks IPv6 support, so this might not be entirely accurate)";
        }else if(c.contains("just you")){
            c = "It's just you. " + args[0] + " looks fine from here.";
        }else{
            c = "isup.me can't find " + args[0] + " on the interwho. This might be because isup.me lacks IPv6 support or simply because you put in an invalid url.";
        }
        IRCUtils.sendMessage(user, network, channel, c, prefix);


    }

}

