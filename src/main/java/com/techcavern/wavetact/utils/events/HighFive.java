package com.techcavern.wavetact.utils.events;

import com.techcavern.wavetact.utils.GeneralRegistry;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class HighFive extends ListenerAdapter<PircBotX> {
    public void onMessage(MessageEvent<PircBotX> event)
            throws Exception {
        if (event.getMessage().toLowerCase().startsWith("o/") ||
                event.getMessage().toLowerCase().startsWith("\\o") ||
                event.getMessage().toLowerCase().startsWith("0/") ||
                event.getMessage().toLowerCase().startsWith("\\0") ||
                event.getMessage().toLowerCase().endsWith("o/") ||
                event.getMessage().toLowerCase().endsWith("\\o") ||
                event.getMessage().toLowerCase().endsWith("0/") ||
                event.getMessage().toLowerCase().endsWith("\\0")) {
            GeneralRegistry.HighFives.add(event.getUser().getNick());
            CheckIfTwoHighFives(event.getChannel());
        }
    }

    void CheckIfTwoHighFives(Channel c) {
        if (((GeneralRegistry.HighFives.size() % 2) == 0) &&
                !GeneralRegistry.HighFives.isEmpty()) {
            c.send()
                    .message(GeneralRegistry.HighFives.get(GeneralRegistry.HighFives.size() -
                            1) + " o/ * \\o " +
                            GeneralRegistry.HighFives.get(GeneralRegistry.HighFives.size() -
                                    2));
        }
    }
}
