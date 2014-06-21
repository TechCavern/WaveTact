package com.techcavern.wavetact.utils;



import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.WhoisEvent;
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
		if (v != null){
			boolean y = GeneralRegistry.Controllers.contains(v.toLowerCase());
			return y;
		} else {
			boolean y = GeneralRegistry.ControllerHostmasks.contains(u.getHostmask());
			return y;
		}

			
		}
	public static int getPermLevel(PircBotX bot, User u, Channel z){
		if (PermUtils.isController(bot, u)){
			return 9001;
		}
		else if(z.isOp(u)|| z.isSuperOp(u) || z.isOwner(u)){
                       return 10;
                    
		} else if (z.isHalfOp(u) || z.hasVoice(u)){
			return 5;
		} else {
			return 0;
		}
	}
}

	

