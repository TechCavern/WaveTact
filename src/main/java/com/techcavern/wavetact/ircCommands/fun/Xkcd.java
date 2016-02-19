package com.techcavern.wavetact.ircCommands.fun;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.RandomUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.io.FileNotFoundException;

@IRCCMD
public class Xkcd extends IRCCommand {

    public Xkcd() {
        super(GeneralUtils.toArray("xkcd x randomxkcd"), 1, "xkcd [comic num#]", "Returns random xkcd comic or specified comic #", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Integer comicnumber = 404;
        JsonObject latestcomic = GeneralUtils.getJsonObject("http://xkcd.com/info.0.json");
        Integer latest = latestcomic.get("num").getAsInt();
        if (args.length > 0) {
            comicnumber = Integer.parseInt(args[0]);
            if (latest < comicnumber) {
                IRCUtils.sendError(user, network, channel, "Comic does not exist", prefix);
                return;
            }
        } else {
            do {
                comicnumber = RandomUtils.nextInt(1, latest);
            } while (comicnumber == 404);
        }
        try {
            JsonObject comic = GeneralUtils.getJsonObject("http://xkcd.com/" + comicnumber + "/info.0.json");
            String date = "Date: " + comic.get("day").getAsString() + "/" + comic.get("month").getAsString() + "/" + comic.get("year").getAsString();
            String num = comic.get("num").getAsString();
            String title = comic.get("title").getAsString();
            IRCUtils.sendMessage(user, network, channel, "[" + num + "] " + date + " - " + title + " - " + GeneralUtils.shortenURL("http://xkcd.com/" + num), prefix);
        } catch (FileNotFoundException e) {
            IRCUtils.sendError(user, network, channel, "Comic does not exist", prefix);
        }
    }
}
