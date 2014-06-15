package com.techcavern.wavetact.utils;

import java.nio.charset.Charset;

import org.pircbotx.PircBotX;
import org.pircbotx.Configuration.Builder;

import com.techcavern.wavetact.commands.CheckPermLevel;

public class IRCUtils {
    public static void createbot(Builder<PircBotX> Net, String Name, String Channel,String Nick, String Server){
	System.out.println("Configuring"+Name);
    Net.setName(Nick);
    Net.setLogin("WaveTact");
    Net.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
    Net.setServer(Server, 6667);
    Net.addAutoJoinChannel(Channel);
    Net.getListenerManager().addListener(new CheckPermLevel());

    }
}
