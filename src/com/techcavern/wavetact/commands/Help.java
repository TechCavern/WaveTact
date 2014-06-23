package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by jztech101 on 6/23/14.
 */
public class Help extends Command {
    public Help() {
        super("help", 0, "help [command] - Generally a + before something means editing it, and a - means removing it. None means adding it. - Time is in [time](s/m/h/d) format, IRCd is specified with c or Charybdis, u for Unreal, and I for inspircd.");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
      event.respond(IRCUtils.getCommand(args[0]).getDesc());

    }
}
