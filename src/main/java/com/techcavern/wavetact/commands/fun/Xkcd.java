package com.techcavern.wavetact.commands.fun;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.RandomUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class Xkcd extends GenericCommand {

    public Xkcd() {
        super(GeneralUtils.toArray("xkcd randomxkcd"), 0, "xkcd [comic num#]", "returns random comic or specified comic #", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Integer comicnumber = 1;
        JsonObject latestcomic = GeneralUtils.getJsonObject("http://xkcd.com/info.0.json");
        Integer latest = latestcomic.get("num").getAsInt();
        if (args.length > 0) {
            comicnumber = Integer.parseInt(args[0]);
            if (latest < comicnumber) {
                ErrorUtils.sendError(user, "comic does not exist");
                return;
            }
        } else {
            comicnumber = RandomUtils.nextInt(1, latest);
        }
        JsonObject comic = GeneralUtils.getJsonObject("http://xkcd.com/" + comicnumber + "/info.0.json");
        String date = "Date: " + comic.get("day") + "/" + comic.get("month") + "/" + comic.get("year");
        String num = comic.get("num").getAsString();
        String title = comic.get("title").getAsString();
        IRCUtils.sendMessage(user, network, channel, num + " - " + date + " - " + title + " - " + "http://xkcd.com/" + num, prefix);
    }
}
