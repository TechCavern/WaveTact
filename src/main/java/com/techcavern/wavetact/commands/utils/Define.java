package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.Constants;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.wordnik.client.api.WordApi;
import com.wordnik.client.model.Definition;
import com.wordnik.client.model.ExampleUsage;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.List;

@CMD
@GenCMD
public class Define extends GenericCommand {

    public Define() {
        super(GeneralUtils.toArray("define whatis"), 0, "Define (Def #) [word]", "defines a word", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (Constants.wordnikapikey == null) {
            ErrorUtils.sendError(user, "Wordnik API key is null - Contact Bot Controller to fix");
        }
        int ArrayIndex = 0;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) - 1;
            args = ArrayUtils.remove(args, 0);
        }
        WordApi api = new WordApi();
        api.getInvoker().addDefaultHeader("api_key", Constants.wordnikapikey);
        List<Definition> Defs = api.getDefinitions(args[0], null, null, null, null, null, null);
        if (Defs.size() > 0) {
            if (Defs.size() - 1 >= ArrayIndex) {
                String word = WordUtils.capitalizeFully(Defs.get(ArrayIndex).getWord());
                String definition = Defs.get(ArrayIndex).getText();
                List<ExampleUsage> examples = Defs.get(ArrayIndex).getExampleUses();
                IRCUtils.sendMessage(user, network, channel, word + ": " + definition, prefix);
                if (examples.size() > 0) {
                    IRCUtils.sendMessage(user, network, channel, "Example: " + examples.get(0).getText(), prefix);
                }
            } else {
                ArrayIndex = ArrayIndex + 1;
                ErrorUtils.sendError(user, "Def #" + ArrayIndex + " does not exist");
            }
        } else {
            ErrorUtils.sendError(user, "Not Defined");
        }

    }

}
