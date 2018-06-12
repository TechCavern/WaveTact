package info.techcavern.wavetact.consoleCommands.network;

import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.PircBotX;

import java.util.HashSet;
import java.util.Set;

import static info.techcavern.wavetactdb.Tables.RELAYS;

@ConCMD
public class ListRelays extends ConsoleCommand {

    public ListRelays() {
        super(GeneralUtils.toArray("listrelays relays"), "listrelays", "lists all networks");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        Set<String> networks = new HashSet<>();
        for (Record e : DatabaseUtils.getRelays()) {
            networks.add(e.getValue(RELAYS.PROPERTY) + ": " + e.getValue(RELAYS.VALUE));
        }
        commandIO.getPrintStream().println(StringUtils.join(networks, ", "));
    }

}
