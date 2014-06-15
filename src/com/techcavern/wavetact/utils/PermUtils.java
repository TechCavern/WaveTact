package com.techcavern.wavetact.utils;


import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.WhoisEvent;
import org.apache.commons.lang3.ArrayUtils;
public class PermUtils {
	@SuppressWarnings("unchecked")
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
		boolean y = ArrayUtils.contains(GeneralRegistry.Controllers,v.toLowerCase());
		return y;

		}
	}
	

