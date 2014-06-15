package com.techcavern.wavetact.utils;

import java.util.Arrays;
import java.util.List;

import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.WhoisEvent;

import com.techcavern.wavetact.Main;

public class PermRegistry {
	public static String getAccount(User x)
    {
        String u = "";
        Main.WaveTact.sendRaw().rawLineNow("WHOIS " + x.getNick());
        WhoisEvent<PircBotX> whois = null;
        u = whois.getRegisteredAs();
        return u;
    }
	public boolean isController (User x){
		String v= getAccount(x);
		String[] Controllers = new String[]{"jztech101"};
		List <String> Controllerlist = Arrays.asList(Controllers);
		boolean y = Controllerlist.contains(v);
		return y;
	}
	
}
