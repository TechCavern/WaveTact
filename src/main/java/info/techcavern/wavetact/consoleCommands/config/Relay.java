package info.techcavern.wavetact.consoleCommands.config;

import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import org.jooq.Record;

import static info.techcavern.wavetactdb.Tables.CONFIG;
import static info.techcavern.wavetactdb.Tables.RELAYS;

@ConCMD
public class Relay extends ConsoleCommand {

    public Relay() {
        super(GeneralUtils.toArray("relay"), "relay (+)(-)[name] [channels to relay in networkname.channelname format separated by commas]", "add/remove relays");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        boolean isModify = false;
        boolean isDelete = false;
        boolean viewonly = false;
        if (args.length < 2) {
            viewonly = true;
        }
        if (args[0].startsWith("-")) {
            args[0] = args[0].replaceFirst("-", "");
            isDelete = true;
        } else if (args[0].startsWith("+")) {
            args[0] = args[0].replaceFirst("\\+", "");
            isModify = true;
        } else {
            args[0] = args[0];
        }
        Record relay = DatabaseUtils.getRelay(args[0]);
        if (relay != null && (isDelete || isModify)) {
            if (isDelete) {
                DatabaseUtils.removeRelay(args[0]);
                commandIO.getPrintStream().println("Relay removed");
            } else if (isModify) {
                if (viewonly)
                    commandIO.getPrintStream().println(relay.getValue(RELAYS.VALUE));
                else {
                    relay.setValue(RELAYS.VALUE, GeneralUtils.buildMessage(1, args.length, args));
                    DatabaseUtils.updateRelay(relay);
                    commandIO.getPrintStream().println("Relay modified");
                }
            }
        } else if (relay == null && !isDelete && !isModify) {
            DatabaseUtils.addRelay(args[0], GeneralUtils.buildMessage(1, args.length, args));
            commandIO.getPrintStream().println("Relay added");
        } else {
            commandIO.getPrintStream().println("Relay already exists (If you were adding) or property does not exist");
        }
    }
}
