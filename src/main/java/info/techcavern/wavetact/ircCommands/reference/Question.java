package info.techcavern.wavetact.ircCommands.reference;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import com.wolfram.alpha.*;
import com.wolfram.alpha.visitor.Visitable;
import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static info.techcavern.wavetactdb.Tables.CONFIG;

@IRCCMD
public class Question extends IRCCommand {

    public Question() {
        super(GeneralUtils.toArray("question q wa wolframalpha"), 1, "question [question]", "Ask wolfram alpha a question!", false);
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
        WAEngine engine = new WAEngine();
        engine.setAppID(wolframalphaapikey);
        engine.addFormat("plaintext");
        WAQuery query = engine.createQuery();
        query.setInput(StringUtils.join(args, " "));
        WAQueryResult queryResult = engine.performQuery(query);
        WAPod[] result = queryResult.getPods();
        List<String> results = new ArrayList<>();
        if (result.length > 0) {
                results.add(GeneralUtils.getWAResult(0, result));
                results.add(GeneralUtils.getWAResult(1, result));
                IRCUtils.sendMessage(user, network, channel,StringUtils.join(results, " - "), prefix);
        } else {
            IRCUtils.sendError(user, network, channel, "Question returned no answers", prefix);
        }
    }

}
