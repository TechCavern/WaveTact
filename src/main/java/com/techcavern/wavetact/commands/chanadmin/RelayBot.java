package com.techcavern.wavetact.commands.chanadmin;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanAdminCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import com.techcavern.wavetact.utils.databaseUtils.RelayUtils;
import com.techcavern.wavetact.utils.objects.ChannelUserProperty;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.PermChannel;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@ChanAdminCMD
public class RelayBot extends GenericCommand {

    public RelayBot() {
        super(GeneralUtils.toArray("relaybot relay"), 18, "relaybot +)(-)[bot] (character before actual message)", "Adds, modifies, or removes a relay bot", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String bot = "";
        if (args[0].startsWith("-")) {
            bot = args[0].replaceFirst("-", "");
        } else if (args[0].startsWith("+")) {
            bot = args[0].replaceFirst("\\+", "");
        } else {
            bot = args[0];
        }
        String account = PermUtils.authUser(network, bot);
        ChannelUserProperty relayBot = GetUtils.getRelayBotbyBotName(network, channel.getName(), account);
        if(args[0].startsWith("-")){
            if(relayBot != null) {
                Registry.RelayBots.remove(relayBot);
                IRCUtils.sendMessage(user, network, channel, bot + " removed from relay bots list", prefix);
                RelayUtils.saveRelayBots();
            }else{
                ErrorUtils.sendError(user, "Relay bot does not exist");
            }
        }else if(args[0].startsWith("\\+")){
            if(relayBot != null){
            relayBot.setProperty(args[1]);
            IRCUtils.sendMessage(user,network, channel, bot + " modified in relay bots list", prefix );
            RelayUtils.saveRelayBots();
            }else{
                ErrorUtils.sendError(user, "Relay bot does not exist");
            }
        }else{
            if(relayBot != null){
                ErrorUtils.sendError(user, "Relay bot already exists!");
            }else{
                Registry.RelayBots.add(new ChannelUserProperty(GetUtils.getNetworkNameByNetwork(network), channel.getName(),account, args[1]));
                IRCUtils.sendMessage(user, network, channel, bot + " added to relay bots list", prefix);
                RelayUtils.saveRelayBots();
            }
        }

    }
}
