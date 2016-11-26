package com.techcavern.wavetact.ircCommands.reference;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.wolfram.alpha.*;
import com.wolfram.alpha.visitor.Visitable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.techcavern.wavetactdb.Tables.CONFIG;

@IRCCMD
public class Question extends IRCCommand {

    public Question() {
        super(GeneralUtils.toArray("question q wa wolframalpha"), 1, "question (answer #) [question]", "Ask wolfram alpha a question!", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String wolframalphaapikey;
        if (DatabaseUtils.getConfig("wolframalphaapikey") != null)
            wolframalphaapikey = DatabaseUtils.getConfig("wolframalphaapikey").getValue(CONFIG.VALUE);
        else {
            IRCUtils.sendError(user, network, channel, "Wolfram Alpha api key is null - contact bot controller to fix", prefix);
            return;
        }
        int ArrayIndex = 1;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]);
            args = ArrayUtils.remove(args, 0);
        }
        WAEngine engine = new WAEngine();
        engine.setAppID(wolframalphaapikey);
        engine.addFormat("plaintext");
        WAQuery query = engine.createQuery();
        query.setInput(StringUtils.join(args, " "));
        WAQueryResult queryResult = engine.performQuery(query);
        WAPod[] result = queryResult.getPods();
        List<String> results = new ArrayList<>();
        if (result.length > 0) {
            if (result.length - 1 >= ArrayIndex) {
                for (WASubpod sub : result[ArrayIndex].getSubpods()) {
                    for (Visitable visitable : sub.getContents()) {
                        if (sub.getTitle().isEmpty())
                            results.add("[" + result[ArrayIndex].getTitle() + "] " + ((WAPlainText) sub.getContents()[0]).getText().replaceAll("\\n", " - ").replaceAll(" \\| ", ": "));
                        else
                            results.add("[" + result[ArrayIndex].getTitle() + " - " + sub.getTitle() + "] " + ((WAPlainText) sub.getContents()[0]).getText().replaceAll("\\n", " - ").replaceAll(" \\| ", ": "));
                    }
                }
                IRCUtils.sendMessage(user, network, channel,StringUtils.join(results, " - "), prefix);
            } else {
                IRCUtils.sendError(user, network, channel, "Answer #" + ArrayIndex + " does not exist", prefix);
            }
        } else {
            IRCUtils.sendError(user, network, channel, "Question returned no answers", prefix);
        }
    }

}
