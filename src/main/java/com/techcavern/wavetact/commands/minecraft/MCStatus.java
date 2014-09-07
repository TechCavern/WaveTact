package com.techcavern.wavetact.commands.minecraft;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.text.WordUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@CMD
@GenCMD
public class MCStatus extends GenericCommand {

    public MCStatus() {
        super(GeneralUtils.toArray("mcstatus"), 0, null, "checks status of MC servers");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String Result;
        URL url = new URL("https://status.mojang.com/check");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        Result = reader.readLine().replace("\"},{\"", " | ").replace("\":\"", ": ").replace("green", "Online").replace("red", "Offline").replace("[{\"", "").replace("\"}]", "").replace(".minecraft.net", "").replace(".mojang.com", "").replace("server", " Server").replace(".net", "");
        Result = WordUtils.capitalizeFully(Result);
        if (Result != null) {
            IRCUtils.SendMessage(user, channel, Result, isPrivate);
        } else {
            IRCUtils.sendError(user, "MC Status Currently Unavailable");
        }
    }

}

