package com.techcavern.wavetact;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.Configuration.Builder;
import org.slf4j.impl.SimpleLogger;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;
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
	        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
	        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
	
	        // create base commands
	        System.out.println("Creating commands...");
		    System.out.println("Commands created.");
		    
		    Builder<PircBotX> Ovd = new Configuration.Builder<PircBotX>();
	        IRCUtils.createbot(Ovd, "Ovd", GeneralRegistry.OvdChannel,GeneralRegistry.OvdNick, GeneralRegistry.OvdServer);
	        
	        System.out.println("Configured.");
	
	        System.out.println("Creating threads...");	 

	        try
	        {
	        	System.out.println("Connecting...");
	            WaveTact.startBot();
	        }
	        catch (Exception e)
	        {
	            throw new RuntimeException(e);
	        }
	    }
	}

