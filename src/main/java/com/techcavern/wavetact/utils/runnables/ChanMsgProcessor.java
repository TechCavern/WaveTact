package com.techcavern.wavetact.utils.runnables;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotz.hooks.events.MessageEvent;

/**
 * Created by jztech101 on 7/27/14.
 */
public class ChanMsgProcessor {
    public static void ChanMsgProcess(MessageEvent event) {
        class process implements Runnable {
            public void run() {
                String[] messageParts = event.getMessage().split(" ");
                String m = messageParts[0].toLowerCase();
                GenericCommand Command = GetUtils.getCommand(m.replaceFirst(GetUtils.getCommandChar(event.getBot()), ""));
                if (Command != null && m.startsWith(GetUtils.getCommandChar(event.getBot()))) {
                    int UserPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel());
                    if (UserPermLevel >= Command.getPermLevel()) {
                        if (Command.getPermLevel() == 9) {
                            if (event.getChannel().isOp(event.getBot().getUserBot()) || event.getChannel().isOp(event.getBot().getUserBot()) || event.getChannel().isOwner(event.getBot().getUserBot())) {
                                try {
                                    Command.onCommand(event.getUser(), event.getBot(), event.getChannel(), false, UserPermLevel, ArrayUtils.remove(messageParts, 0));
                                } catch (Exception e) {
                                }
                            } else {
                                event.getUser().send().notice("Error: I must be op or higher to perform the operation requested");
                            }
                        } else if (Command.getPermLevel() == 14) {

                            if (event.getChannel().isOwner(event.getBot().getUserBot())) {
                                try {
                                    Command.onCommand(event.getUser(), event.getBot(), event.getChannel(), false, UserPermLevel, ArrayUtils.remove(messageParts, 0));
                                } catch (Exception e) {
                                }
                            } else {
                                event.getUser().send().notice("Error: I must be owner to perform the operation requested");
                            }
                        } else if (Command.getPermLevel() == 6) {
                            if (event.getChannel().isOwner(event.getBot().getUserBot()) || event.getChannel().isOp(event.getBot().getUserBot()) || event.getChannel().isHalfOp(event.getBot().getUserBot()) || event.getChannel().isSuperOp(event.getBot().getUserBot())) {
                                try {
                                    Command.onCommand(event.getUser(), event.getBot(), event.getChannel(), false, UserPermLevel, ArrayUtils.remove(messageParts, 0));
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
                                Command.onCommand(event.getUser(), event.getBot(), event.getChannel(), false, UserPermLevel, ArrayUtils.remove(messageParts, 0));
                            } catch (Exception e) {

                            }
                        }

                    } else {
                        event.getUser().send().notice("Permission Denied");

                    }
                }
            }
        }
        GeneralRegistry.threadPool.execute(new process());
    }

}
