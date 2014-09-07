package com.techcavern.wavetact.utils.runnables;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;


public class ChanMsgProcessor {
    public static void ChanMsgProcess(MessageEvent<PircBotX> event) {
        class process implements Runnable {
            public void run() {
                String[] messageParts = Colors.removeFormattingAndColors(event.getMessage()).replaceAll("\\P{InBasic_Latin}", "").split(" ");
                String m = messageParts[0].toLowerCase();
                messageParts = ArrayUtils.remove(messageParts, 0);
                GenericCommand Command = GetUtils.getCommand(m.replaceFirst(GetUtils.getCommandChar(event.getBot()), ""));
                if (Command != null && m.startsWith(GetUtils.getCommandChar(event.getBot()))) {
                    int UserPermLevel = PermUtils.getAuthPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel(), PermUtils.getAuthedAccount(event.getBot(), event.getUser().getNick()));
                    if (UserPermLevel >= Command.getPermLevel()) {
                        IRCUtils.processMessage(Command, event.getBot(), event.getChannel(), event.getUser(), UserPermLevel, messageParts, false);
                    } else {
                        event.getUser().send().notice("Permission Denied");
                    }
                }
            }
        }
        GeneralRegistry.threadPool.execute(new process());
    }

}
