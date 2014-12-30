package com.techcavern.wavetact.utils.runnables;

import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.objects.ChannelUserProperty;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Colors;
import org.pircbotx.hooks.events.MessageEvent;


public class RelayMsgProcessor {
    public static void RelayMsgProcess(final MessageEvent event) {
        class process implements Runnable {
            public void run() {
                ChannelUserProperty relayBot = GetUtils.getRelayBotbyBotName(event.getBot(), event.getChannel().getName(), PermUtils.authUser(event.getBot(), event.getUser().getNick()));
                String startingmessage = event.getMessage();
                if (relayBot != null) {
                    String[] midmessage = StringUtils.split(startingmessage, relayBot.getProperty());
                    if (midmessage.length > 1)
                        startingmessage = midmessage[1];
                    else
                        return;
                }else{
                    return;
                }
                String[] message = StringUtils.split(Colors.removeFormattingAndColors(startingmessage), " ");
                String command = message[0].toLowerCase();
                message = ArrayUtils.remove(message, 0);
                GenericCommand Command = GetUtils.getGenericCommand(StringUtils.replaceOnce(command, GetUtils.getCommandChar(event.getBot()), ""));
                if (Command != null && command.startsWith(GetUtils.getCommandChar(event.getBot())) && Command.getPermLevel() == 0) {
                    try {
                        Command.onCommand(event.getUser(), event.getBot(), IRCUtils.getPrefix(event.getBot(), event.getChannelSource()), event.getChannel(), false, 0, message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Registry.threadPool.execute(new process());
    }
}


