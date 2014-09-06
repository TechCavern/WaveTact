package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.wolfram.alpha.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

@CMD
@GenCMD
public class Question extends GenericCommand {

    public Question() {
        super(GeneralUtils.toArray("question"), 0, "question (Answer #) [Question]", "ask us a question!");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        int ArrayIndex = 1;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) + 1;
            args = ArrayUtils.remove(args, 0);
        }
        WAEngine engine = new WAEngine();
        engine.setAppID(GeneralRegistry.wolframalphaapikey);
        engine.addFormat("plaintext");
        WAQuery query = engine.createQuery();
        query.setInput(StringUtils.join(args, " "));
        WAQueryResult queryResult = engine.performQuery(query);
        List<String> waResults = new ArrayList<>();
        for (WAPod pod : queryResult.getPods()) {
            for (WASubpod spod : pod.getSubpods()) {
                for (Object e : spod.getContents()) {
                    if (e instanceof WAPlainText) {
                        waResults.add(((WAPlainText) e).getText().replaceAll("[|]", "").replaceAll("\n", ", ").replaceAll("  ", " "));
                    }
                }
            }
        }
        if (waResults.size() > 0) {
            if (waResults.size()-1 >= ArrayIndex && !waResults.get(ArrayIndex).isEmpty()) {
                IRCUtils.SendMessage(user, channel, waResults.get(0) + ": " + StringUtils.substring(waResults.get(ArrayIndex), 0, 750), isPrivate);

            } else {
                ArrayIndex = ArrayIndex -1;
                IRCUtils.sendError(user, "Answer #" + ArrayIndex + " does not exist");
            }
        } else {
            IRCUtils.sendError(user, "Question returned no answers");
        }
    }

}
