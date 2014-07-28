package com.techcavern.wavetact.utils.runnables;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotz.Channel;
import org.pircbotz.hooks.events.PrivateMessageEvent;

/**
 * Created by jztech101 on 7/27/14.
 */
public class PrivMsgProcessor {

    public static void PrivMsgProcess(PrivateMessageEvent event) {
        class process implements Runnable {
            public void run() {
                String[] messageParts = event.getMessage().split(" ");
                String m = messageParts[0].toLowerCase();
                GenericCommand Command = GetUtils.getCommand(m);
                if (Command == null) {
                    Command = GetUtils.getCommand(m.replaceFirst(GetUtils.getCommandChar(event.getBot()), ""));
                }
                messageParts = ArrayUtils.remove(messageParts, 0);
                Channel channel = null;
                if (messageParts.length >= 1 && messageParts[0].startsWith("#")) {
                    channel = GetUtils.getChannelbyName(event.getBot(), messageParts[0]);
                    messageParts = ArrayUtils.remove(messageParts, 0);
                }
                if (Command != null) {
                    if ((Command.getPermLevel() >= 0 && (Command.getPermLevel() <= 5 || Command.getPermLevel() > 18) || (channel != null))) {
                        int UserPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), channel);
                        if (UserPermLevel >= Command.getPermLevel()) {
                            if (Command.getPermLevel() == 9) {
                                if (channel.isOp(event.getBot().getUserBot()) || channel.isOp(event.getBot().getUserBot()) || channel.isOwner(event.getBot().getUserBot())) {
                                    try {
                                        Command.onCommand(event.getUser(), event.getBot(), channel, true, UserPermLevel, messageParts);
                                    } catch (Exception e) {

                                    }
                                } else {
                                    event.getUser().send().notice("Error: I must be at op or higher to perform the operation requested");
                                }
                            } else if (Command.getPermLevel() == 14) {

                                if (channel.isOwner(event.getBot().getUserBot())) {
                                    try {
                                        Command.onCommand(event.getUser(), event.getBot(), channel, true, UserPermLevel, messageParts);
                                    } catch (Exception e) {

                                    }
                                } else {
                                    event.getUser().send().notice("Error: I must be owner to perform the operation requested");
                                }
                            } else if (Command.getPermLevel() == 6) {
                                if (channel.isOwner(event.getBot().getUserBot()) || channel.isOp(event.getBot().getUserBot()) || channel.isHalfOp(event.getBot().getUserBot()) || channel.isSuperOp(event.getBot().getUserBot())) {
                                    try {
                                        Command.onCommand(event.getUser(), event.getBot(), channel, true, UserPermLevel, messageParts);
                                    } catch (Exception e) {

                                    }
                                } else {
                                    if (event.getBot().getServerInfo().getPrefixes().contains("h")) {
                                        event.getUser().send().notice("Error: I must be half-op or higher to perform the operation requested");
                                    } else {
                                        event.getUser().send().notice("Error: I must be op or higher to perform the operation requested");
                                    }
                                }
                            } else {
                                try {
                                    Command.onCommand(event.getUser(), event.getBot(), channel, true, UserPermLevel, messageParts);
                                } catch (Exception e) {

                                }
                            }

                        } else {
                            event.getUser().send().message("Permission Denied");
                        }
                    } else {
                        event.getUser().send().message("Channel Must be Specified as argument #1");
                    }
                }
            }
        }
        GeneralRegistry.threadPool.execute(new process());


    }

}
