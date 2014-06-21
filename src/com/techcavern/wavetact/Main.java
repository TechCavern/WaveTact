package com.techcavern.wavetact;


import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;
import org.slf4j.impl.SimpleLogger;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;


	public class Main
	{	    
            public static MultiBotManager<PircBotX> WaveTact = new MultiBotManager<>();
	    public static void main(String[] args) throws Exception 
	    {
	        System.out.println("Starting...");
	        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
	        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
	        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
                IRCUtils.RegisterCommands();
	        
	        String pwd = null;
	        String g = args[0];
	        PircBotX Ovd = IRCUtils.createbot(pwd,"Ovd", GeneralRegistry.OvdChannels, GeneralRegistry.OvdNick, GeneralRegistry.OvdServer);
	        PircBotX Esper = IRCUtils.createbot(g,"Esper", GeneralRegistry.EsperChannels, GeneralRegistry.EsperNick, GeneralRegistry.EsperServer);
	        PircBotX ECode = IRCUtils.createbot(pwd,"ECode", GeneralRegistry.ECodeChannels, GeneralRegistry.ECodeNick, GeneralRegistry.ECodeServer);
	        PircBotX Xertion = IRCUtils.createbot(pwd,"Xertion", GeneralRegistry.XertionChannels, GeneralRegistry.XertionNick, GeneralRegistry.XertionServer);
	        
	        WaveTact.addBot(Esper);
                WaveTact.addBot(Ovd);
	        WaveTact.addBot(Xertion);
	        WaveTact.addBot(ECode);
	        WaveTact.start();
	    }
  
	}

