package com.techcavern.wavetact.consoleCommands.network;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.PircBotX;

import java.util.HashSet;
import java.util.Set;

import static com.techcavern.wavetactdb.Tables.RELAYS;

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
