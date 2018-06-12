/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.techcavern.wavetact.eventListeners;

import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ModeEvent;

import static info.techcavern.wavetactdb.Tables.CHANNELPROPERTY;


/**
 * @author jztech101
 */
public class BanListener extends ListenerAdapter {

    public void onMode(ModeEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                String banMask = event.getMode();
                String network = IRCUtils.getNetworkNameByNetwork(event.getBot());
                if (event.getUser() == null ||
                        event.getUser().getNick().equalsIgnoreCase(event.getBot().getNick()) || !(event.getChannel().isHalfOp(event.getBot().getUserBot()) ||
                        event.getChannel().isOp(event.getBot().getUserBot()) || event.getChannel().isSuperOp(event.getBot().getUserBot())
                        || event.getChannel().isOwner(event.getBot().getUserBot()))) {
                    return;
                }
                String type;
                boolean ban = false;
                boolean isMute;
                if (banMask.startsWith("+")) {
                    ban = true;
                    banMask = banMask.replaceFirst("\\+", "");
                } else if (banMask.startsWith("-")) {
                    ban = false;
                    banMask = banMask.replaceFirst("-", "");
                }
                if (ban && (DatabaseUtils.getChannelProperty(network, event.getChannel().getName(), "autounban") == null || DatabaseUtils.getChannelProperty(network, event.getChannel().getName(), "autounban").getValue(CHANNELPROPERTY.VALUE) == null)) {
                    return;
                }
                if (banMask.startsWith("q ") && event.getBot().getServerInfo().getChannelModes().contains("q")) {
                    type = "q ";
                    isMute = true;
                } else if (banMask.startsWith("b ~q:") && event.getBot().getServerInfo().getExtBanPrefix() != null && event.getBot().getServerInfo().getExtBanPrefix().equalsIgnoreCase("~") && event.getBot().getServerInfo().getExtBanList() != null && event.getBot().getServerInfo().getExtBanList().contains("q")) {
                    type = "b ~q:";
                    isMute = true;
                } else if (banMask.startsWith("b m:") && event.getBot().getServerInfo().getExtBanList().contains("m") && event.getBot().getServerInfo().getExtBanPrefix() == null) {
                    type = "b m:";
                    isMute = true;
                } else if (banMask.startsWith("b ")) {
                    type = "b ";
                    isMute = false;
                } else {
                    return;
                }
                banMask = banMask.replaceFirst(type, "");
                if (!ban) {
                    DatabaseUtils.removeBan(network, event.getChannel().getName(), banMask, isMute);
                } else if (ban) {
                    DatabaseUtils.addBan(network, event.getChannel().getName(), banMask, System.currentTimeMillis(), GeneralUtils.getMilliSeconds(DatabaseUtils.getChannelProperty(network, event.getChannel().getName(), "autounban").getValue(CHANNELPROPERTY.VALUE)), isMute, type);
                }
            }
        }
        Registry.threadPool.execute(new process());
    }

}
