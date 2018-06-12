/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.techcavern.wavetact.ircCommands.fun;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.PermUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import info.techcavern.wavetact.utils.*;
/**
 * @author jztech101
 */
@IRCCMD
public class Say extends IRCCommand {

    public Say() {
        super(GeneralUtils.toArray("say msg s a act do prism echo"), 20, "say [something]", "Makes the bot say something", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
            String channelname = null;
	if(!isPrivate){ channelname = prefix + channel.getName(); } else { channelname = user.getNick(); }
	if(args.length > 1 && args[0].startsWith("#")){channelname = args[0];  args=ArrayUtils.remove(args,0); }
	    if (command.equalsIgnoreCase("act") || command.equalsIgnoreCase("do") || command.equalsIgnoreCase("a")) {
                Registry.messageQueue.get(network).add("PRIVMSG " + channelname + " :\u0001ACTION " + StringUtils.join(args," ") + "\u0001");    
            } else if (command.equalsIgnoreCase("prism")) {
                Registry.messageQueue.get(network).add("PRIVMSG " + channelname + " :" + GeneralUtils.prism(StringUtils.join(args," ")));
            } else {
               if(args.length > 1 && !Character.isLetterOrDigit(args[0].charAt(0)) && args[0].charAt(1) == "#".charAt(0)){
		channelname = args[0]; args = ArrayUtils.remove(args,0);
		}
		Registry.messageQueue.get(network).add("PRIVMSG " + channelname + " :" + StringUtils.join(args," "));
            }
    }
}



