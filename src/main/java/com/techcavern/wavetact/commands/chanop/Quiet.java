package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.objects.UTime;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public class Quiet extends Command {
    @CMD
    public Quiet() {
        super(GeneralUtils.toArray("quiet mute m"), 9, "Quiet [ircd] (-)[User] (time)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
        String h;
        if(args[1].contains("!") && args[0].contains("@")){
            h = args[1];
        }else{
            if(args[1].startsWith("+"))
            h=IRCUtils.getUserByNick(event.getChannel(), args[1].replaceFirst("\\+", "")).getHostmask();
            else if(args[1].startsWith("-"))
                h=IRCUtils.getUserByNick(event.getChannel(), args[1].replaceFirst("-", "")).getHostmask();
            else
                h=IRCUtils.getUserByNick(event.getChannel(), args[1]).getHostmask();

        }
        if ((!args[1].startsWith("-")) && (!args[1].startsWith("+"))) {
            if (IRCUtils.getQuietTime(h) == null) {

                if (args.length == 3) {
                    quiet(h,args[0],
                            event.getChannel(), event.getBot());
                    UTime c = new UTime(h, event.getBot().getServerInfo().getNetwork(), args[0], event.getChannel().getName(), GeneralUtils.getMilliSeconds(args[2]) + System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);
                    IRCUtils.saveQuietTimes();

                } else if (args.length < 3) {
                    quiet(h, args[0], event.getChannel(), event.getBot());
                    UTime c = new UTime(h, event.getBot().getServerInfo().getNetwork(), args[0], event.getChannel().getName(), GeneralUtils.getMilliSeconds("7w") + System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);
                    IRCUtils.saveQuietTimes();

                }
            } else {
                event.getChannel().send().message("Quiet already exists!");
            }
        } else {
            if (IRCUtils.getQuietTime(h) != null) {
                if (args[1].startsWith("-")) {
                    IRCUtils.getQuietTime(h).setTime(0);
                    IRCUtils.saveQuietTimes();
                } else if (args[1].startsWith("+")) {
                    if(args[1].startsWith("+")) {
                        IRCUtils.getQuietTime(h).setTime(IRCUtils.getQuietTime(h).getTime() + GeneralUtils.getMilliSeconds(args[2].replace("+", "")));
                    }else
                    if(args[1].startsWith("-")) {
                        IRCUtils.getQuietTime(h).setTime(IRCUtils.getQuietTime(h).getTime() - GeneralUtils.getMilliSeconds(args[2].replace("-", "")));

                    }else{
                        IRCUtils.getQuietTime(h).setTime(GeneralUtils.getMilliSeconds(args[2].replace("-", "")));
                    }
                    event.getChannel().send().message("Quiet Modified");
                    IRCUtils.saveQuietTimes();
                } else {
                    event.getChannel().send().message("Quiet does not exist!");
                }
            }
        }
    }

    void quiet(String g, String i, Channel c, PircBotX b) {
        switch(i.toLowerCase()) {
            case "c":
            IRCUtils.setMode(c, b, "+q ",g);
                break;
            case "u":
        IRCUtils.setMode(c, b, "+b ~q:", g);
                break;
            case "i":
            IRCUtils.setMode(c, b, "+b m:", g);
                break;
        }
    }
}
