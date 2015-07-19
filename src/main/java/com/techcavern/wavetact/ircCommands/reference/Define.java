package com.techcavern.wavetact.ircCommands.reference;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.wordnik.client.api.WordApi;
import com.wordnik.client.model.Definition;
import com.wordnik.client.model.ExampleUsage;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.List;

import static com.techcavern.wavetactdb.Tables.CONFIG;

@IRCCMD
public class Define extends IRCCommand {

    public Define() {
        super(GeneralUtils.toArray("define d def"), 0, "define (def #) [word]", "Defines a word", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String wordnikapikey;
        if (DatabaseUtils.getConfig("wordnikapikey") != null)
            wordnikapikey = DatabaseUtils.getConfig("wordnikapikey").getValue(CONFIG.VALUE);
        else {
            IRCUtils.sendError(user, network, channel, "Wordnik api key is null - contact bot controller to fix", prefix);
            return;
        }
        int ArrayIndex = 0;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) - 1;
            args = ArrayUtils.remove(args, 0);
        }
        WordApi api = new WordApi();
        api.getInvoker().addDefaultHeader("api_key", wordnikapikey);
        List<Definition> Defs = api.getDefinitions(args[0], null, null, null, null, null, null);
        if (Defs.size() > 0) {
            if (Defs.size() - 1 >= ArrayIndex) {
                String word = WordUtils.capitalizeFully(Defs.get(ArrayIndex).getWord());
                String definition = Defs.get(ArrayIndex).getText();
                List<ExampleUsage> examples = Defs.get(ArrayIndex).getExampleUses();
                IRCUtils.sendMessage(user, network, channel, "[" + word + "] " + definition, prefix);
                if (examples.size() > 0) {
                    IRCUtils.sendMessage(user, network, channel, "Example: " + examples.get(0).getText(), prefix);
                }
            } else {
                ArrayIndex = ArrayIndex + 1;
                IRCUtils.sendError(user, network, channel, "Def #" + ArrayIndex + " does not exist", prefix);
            }
        } else {
            IRCUtils.sendError(user, network, channel, "Not defined", prefix);
        }

    }

}
