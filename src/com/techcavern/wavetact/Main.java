package com.techcavern.wavetact;


import com.techcavern.wavetact.commands.Ban;
import com.techcavern.wavetact.commands.BasicChanOp;
import com.techcavern.wavetact.commands.BasicCommands;
import com.techcavern.wavetact.commands.CheckUserLevel;
import com.techcavern.wavetact.commands.ControllerCommands;
import com.techcavern.wavetact.commands.Quiet;
import org.pircbotx.MultiBotManager;
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
                System.out.println("Creating Commands");
                new Ban();
                new BasicChanOp();
                new BasicCommands();
                new CheckUserLevel();
                new ControllerCommands();
                new Quiet();
	        
	        String x = null;
	        String g = args[0];
	        PircBotX Ovd = IRCUtils.createbot(x,"Ovd", GeneralRegistry.OvdChannels, GeneralRegistry.OvdNick, GeneralRegistry.OvdServer);
	        PircBotX Esper = IRCUtils.createbot(g,"Esper", GeneralRegistry.EsperChannels, GeneralRegistry.EsperNick, GeneralRegistry.EsperServer);
	        PircBotX ECode = IRCUtils.createbot(x,"ECode", GeneralRegistry.ECodeChannels, GeneralRegistry.ECodeNick, GeneralRegistry.ECodeServer);
	        PircBotX Xertion = IRCUtils.createbot(x,"Xertion", GeneralRegistry.XertionChannels, GeneralRegistry.XertionNick, GeneralRegistry.XertionServer);

	        MultiBotManager<PircBotX> WaveTact = new MultiBotManager<>();
	        WaveTact.addBot(Esper);
//	        WaveTact.addBot(Ovd);
	        WaveTact.addBot(Xertion);
	        WaveTact.addBot(ECode);
	        WaveTact.start();
	    }
	}

