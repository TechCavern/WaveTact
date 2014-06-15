package com.techcavern.wavetact.utils;

import java.util.Arrays;
import java.util.List;

import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.WhoisEvent;

public class PermUtils {
	public static String getAccount(PircBotX bot,User u)
    {
		        String user = "";
		        bot.sendRaw().rawLineNow("WHOIS " + u.getNick());
		        WaitForQueue waitForQueue = new WaitForQueue(bot);
		        WhoisEvent<PircBotX> test = null;
		        try
		        {
		            test = waitForQueue.waitFor(WhoisEvent.class);
		            waitForQueue.close();
		            user = test.getRegisteredAs();
		        }
		        catch (InterruptedException ex)
		        {
		            ex.printStackTrace();
		            user = null;
		        }

		        return user;
    }
	public static boolean isController (PircBotX bot,User u){
		String v= getAccount(bot, u);
		List <String> Controllerlist = Arrays.asList(GeneralRegistry.Controllers);
		boolean y = Controllerlist.contains(v.toLowerCase());
		return y;
	}
	
}
