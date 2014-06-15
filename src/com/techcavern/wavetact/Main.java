package com.techcavern.wavetact;


import org.pircbotx.PircBotX;
import org.slf4j.impl.SimpleLogger;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;


	public class Main
	{	    
	    public static void main(String[] args) throws Exception 
	    {
	        System.out.println("Starting...");
	        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
	        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
	        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");

	        

	        
	        PircBotX Ovd = IRCUtils.createbot("Ovd", GeneralRegistry.OvdChannel,GeneralRegistry.OvdNick, GeneralRegistry.OvdServer);
	        Ovd.startBot();
	    }
	}

