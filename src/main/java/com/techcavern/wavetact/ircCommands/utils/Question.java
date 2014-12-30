package com.techcavern.wavetact.ircCommands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.objects.IRCCommand;
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
public class Question extends IRCCommand {

    public Question() {
        super(GeneralUtils.toArray("question wa wolframalpha"), 0, "question (answer #) [question]", "Ask wolfram alpha a question!", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {

        if (Registry.wolframalphaapikey == null) {
            ErrorUtils.sendError(user, "Wolfram Alpha api key is null - contact bot controller to fix");
            return;
        }
        int ArrayIndex = 0;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) - 1;
            args = ArrayUtils.remove(args, 0);
        }
        WAEngine engine = new WAEngine();
        engine.setAppID(Registry.wolframalphaapikey);
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
            if (waResults.size() - 1 >= ArrayIndex && !waResults.get(ArrayIndex).isEmpty()) {
                String result = waResults.get(ArrayIndex);
                if (result.length() > 350) {
                    result = StringUtils.substring(result, 0, 350) + "...";
                }
                IRCUtils.sendMessage(user, network, channel, waResults.get(0) + ": " + result, prefix);

            } else {
                ArrayIndex = ArrayIndex - 1;
                ErrorUtils.sendError(user, "Answer #" + ArrayIndex + " does not exist");
            }
        } else {
            ErrorUtils.sendError(user, "Question returned no answers");
        }
    }

}
