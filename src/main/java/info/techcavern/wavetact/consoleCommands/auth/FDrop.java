package info.techcavern.wavetact.consoleCommands.auth;

import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.*;
import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.*;
import org.jooq.Record;


@ConCMD
public class FDrop extends ConsoleCommand {

    public FDrop() {
        super(GeneralUtils.toArray("fdrop"), "fdrop [user]", "Forcefully drops a user");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) {
        Registry.networks.inverse().keySet().stream().filter(net -> PermUtils.isAccountEnabled(net)).forEach(net -> {
            String authedUser = PermUtils.getAuthedUser((net), IRCUtils.getHostmask(net, args[0], false));
            Registry.authedUsers.get(net).keySet().stream().filter(key -> Registry.authedUsers.get(net).get(key).equals(authedUser)).forEach(key ->
                            Registry.authedUsers.get(net).remove(key)
            );
            DatabaseUtils.removeNetworkUserPropertyByUser(IRCUtils.getNetworkNameByNetwork(net), authedUser);
            DatabaseUtils.removeChannelUserPropertyByUser(IRCUtils.getNetworkNameByNetwork(net), authedUser);
        });
        Record account = DatabaseUtils.getAccount(args[0]);
        if (account != null) {
            DatabaseUtils.removeAccount(args[0]);
            commandIO.getPrintStream().println("Account dropped");
        } else {
            commandIO.getPrintStream().println("Account does not exist");
        }
    }
}
