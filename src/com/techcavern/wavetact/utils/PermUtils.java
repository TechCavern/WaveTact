package com.techcavern.wavetact.utils;

import java.util.Arrays;
import java.util.List;

import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.WhoisEvent;

public class PermUtils {
	@SuppressWarnings("null")
	public static String getAccount(User x, MessageEvent<PircBotX> event)
    {
        String u = "";
        event.getBot().sendRaw().rawLineNow("WHOIS " + x.getNick());
        WhoisEvent<PircBotX> whois = null;
        u = whois.getRegisteredAs();
        return u;
    }
	public boolean isController (User x, MessageEvent<PircBotX> event){
		String v= getAccount(x, event);
		List <String> Controllerlist = Arrays.asList(GeneralRegistry.Controllers);
		boolean y = Controllerlist.contains(v.toLowerCase());
		return y;
	}
	
}
