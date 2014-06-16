package com.techcavern.wavetact.utils;

import java.nio.charset.Charset;

import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techcavern.wavetact.commands.BasicCommands;
import com.techcavern.wavetact.commands.CheckUserLevel;
//import com.techcavern.wavetact.commands.TestCommand;

public class IRCUtils{
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
	public static PircBotX createbot(String Name, String Channel,String Channel2,String Nick, String Server) throws Exception{
    System.out.println("Configuring "+Name);
    Builder<PircBotX> Net = new Configuration.Builder<PircBotX>();
    Net.setName(Nick);
    Net.setLogin("WaveTact");
    Net.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
    Net.setServer(Server, 6667);
    Net.addAutoJoinChannel(Channel);
    Net.addAutoJoinChannel(Channel2);
    Net.getListenerManager().addListener(new BasicCommands());
    Net.getListenerManager().addListener(new CheckUserLevel());
  //  Net.getListenerManager().addListener(new TestCommand());
    PircBotX Bot = new PircBotX(Net.buildConfiguration());
    return Bot;
	}
	public static void onMessage(String command, String result, Channel c, User u, PircBotX bot, String message, int level) throws Exception {
        if (message.equalsIgnoreCase((GeneralRegistry.CommandChar + command))){
        	
        	if (level <= PermUtils.getPermLevel(bot, u, c)){
        		c.send().message(message);
        	} else {
            	c.send().message("Permission Denied");

        	}
        }

}


	
}

