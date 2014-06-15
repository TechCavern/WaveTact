package com.techcavern.wavetact;

import java.nio.charset.Charset;

import com.techcavern.wavetact.commands.*;

import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.managers.ListenerManager;
import org.pircbotx.hooks.managers.ThreadedListenerManager;
import org.slf4j.impl.SimpleLogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

	public class Main
	{
	    public static PircBotX WaveTact;
	    
	    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	    public static void main(String[] args)
	    {
	        System.out.println("Starting...");
	        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
	        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[MM/dd HH:mm:ss]");
	        System.setProperty(SimpleLogger.SHOW_THREAD_NAME_KEY, "false");
	        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
	        System.setProperty(SimpleLogger.SHOW_LOG_NAME_KEY, "false");
	
	        // create base commands
	        System.out.println("Creating commands...");
            ListenerManager<PircBotX> WaveTactListener = new ThreadedListenerManager<PircBotX>();
            WaveTactListener.addListener(new CheckPermLevel());
		        System.out.println("Commands created.");
	
	
	        System.out.println("Configuring bot...");
	        Builder<PircBotX> ovd = new Configuration.Builder<PircBotX>();
	        ovd.setName("WaveTact");
	        ovd.setLogin("WaveTact");
	        ovd.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
	        ovd.setServer("irc.overdrive.pw", 6667);
	        ovd.addAutoJoinChannel("#techcavern");
//	        ovd.addAutoJoinChannel("#dev");
	
	        
	        WaveTact = new PircBotX(ovd.buildConfiguration());
	        System.out.println("Configured.");
	
	        System.out.println("Creating threads...");	        
	        try
	        {
	        	System.out.println("Connecting bot...");
	            WaveTact.startBot();
	        }
	        catch (Exception e)
	        {
	            throw new RuntimeException(e);
	        }
	    }
	}

