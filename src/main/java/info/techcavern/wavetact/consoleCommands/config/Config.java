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

@ConCMD
public class Config extends ConsoleCommand {

    public Config() {
        super(GeneralUtils.toArray("config conf"), "config (+)(-)[property] [value]", "config the bot");
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
        Record config = DatabaseUtils.getConfig(args[0]);
        if (config != null && (isDelete || isModify)) {
            if (isDelete) {
                DatabaseUtils.removeConfig(args[0]);
                commandIO.getPrintStream().println("Property removed");
            } else if (isModify) {
                if (viewonly)
                    commandIO.getPrintStream().println(config.getValue(CONFIG.VALUE));
                else {
                    config.setValue(CONFIG.VALUE, GeneralUtils.buildMessage(1, args.length, args));
                    DatabaseUtils.updateConfig(config);
                    commandIO.getPrintStream().println("Property modified");
                }
            }
        } else if (config == null && !isDelete && !isModify) {
            DatabaseUtils.addConfig(args[0], GeneralUtils.buildMessage(1, args.length, args));
            commandIO.getPrintStream().println("Property added");
        } else {
            commandIO.getPrintStream().println("property already exists (If you were adding) or property does not exist");
        }
    }
}
