package com.techcavern.wavetact.utils.runnables;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * Created by jztech101 on 7/27/14.
 */
public class PrivMsgProcessor {

    public static void PrivMsgProcess(PrivateMessageEvent<PircBotX> event) {
        class process implements Runnable {
            public void run() {
                String[] messageParts = event.getMessage().replaceAll("\\P{InBasic_Latin}", "").split(" ");
                String m = messageParts[0].toLowerCase();
                GenericCommand Command = GetUtils.getCommand(m);
                if (Command == null) {
                    Command = GetUtils.getCommand(m.replaceFirst(GetUtils.getCommandChar(event.getBot()), ""));
                }
                messageParts = ArrayUtils.remove(messageParts, 0);
                if (Command != null) {
                    if ((Command.getPermLevel() >= 0 && (Command.getPermLevel() <= 3 || Command.getPermLevel() > 18))) {
                        int UserPermLevel = PermUtils.getAuthPermLevel(event.getBot(), event.getUser().getNick(), null,PermUtils.getAuthedAccount(event.getBot(), event.getUser().getNick()));
                        if (UserPermLevel >= Command.getPermLevel()) {
                            IRCUtils.processMessage(Command, event.getBot(), null, event.getUser(), UserPermLevel, messageParts, true);
                        } else {
                            event.getUser().send().message("Permission Denied");
                        }
                    } else{
                        Channel channel = null;
                        if (messageParts.length >= 1 && messageParts[0].startsWith("#")) {
                            channel = GetUtils.getChannelbyName(event.getBot(), messageParts[0]);
                            messageParts = ArrayUtils.remove(messageParts, 0);
                        }
                        if (channel != null || Command.getPermLevel() == 5) {
                            int UserPermLevel = PermUtils.getAuthPermLevel(event.getBot(), event.getUser().getNick(), channel, PermUtils.getAuthedAccount(event.getBot(), event.getUser().getNick()));
                            if (UserPermLevel >= Command.getPermLevel()) {
                                IRCUtils.processMessage(Command, event.getBot(), channel, event.getUser(), UserPermLevel, messageParts, true);
                            } else {
                                event.getUser().send().message("Permission Denied");
                            }
                        } else {
                            event.getUser().send().message("Channel Must be Specified as argument #1");
                        }
                    }
                }
            }
        }
        GeneralRegistry.threadPool.execute(new process());


    }

}
