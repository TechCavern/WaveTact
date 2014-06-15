package com.techcavern.wavetact.utils;

import java.util.Arrays;
import java.util.List;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.WhoisEvent;

public class PermUtils {
	@SuppressWarnings("null")
	public static String getAccount(MessageEvent<PircBotX> event)
    {
        String u = "";
        event.getBot().sendRaw().rawLineNow("WHOIS " + event.getUser().getNick());
        WhoisEvent<PircBotX> whois = null;
        u = whois.getRegisteredAs();
        return u;
    }
	public static boolean isController (MessageEvent<PircBotX> event){
		String v= getAccount(event);
		List <String> Controllerlist = Arrays.asList(GeneralRegistry.Controllers);
		boolean y = Controllerlist.contains(v.toLowerCase());
		return y;
	}
	
}
