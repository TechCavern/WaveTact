package com.techcavern.wavetact.utils;

import java.nio.charset.Charset;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.Configuration.Builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techcavern.wavetact.commands.CheckPermLevel;

public class IRCUtils {
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
	public static PircBotX createbot(String Name, String Channel,String Nick, String Server) throws Exception{
    System.out.println("Configuring"+Name);
    Builder<PircBotX> Net = new Configuration.Builder<PircBotX>();
    Net.setName(Nick);
    Net.setLogin("WaveTact");
    Net.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
    Net.setServer(Server, 6667);
    Net.addAutoJoinChannel(Channel);
    Net.getListenerManager().addListener(new CheckPermLevel());
    PircBotX Bot = new PircBotX(Net.buildConfiguration());
    return Bot;
}
}
