package com.techcavern.wavetact;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

public class Main {
	        public static void main(String[] args) throws Exception {
	        	Configuration wavetactconfig = new Configuration.Builder()
	            .setName("WaveTact") 
	            .setLogin("WaveTact") 
	            .setAutoNickChange(true) 
	            .setCapEnabled(true)
	            .setServerHostname("irc.overdrive.pw")
	            .addAutoJoinChannel("#techcavern") 
	            .buildConfiguration();
	    PircBotX wavetactbot = new PircBotX(wavetactconfig);
	    try {
	            wavetactbot.connect();
	    } catch (Exception ex) {
	             ex.printStackTrace();
	    }

	        }
	}

