package com.techcavern.wavetact.consoleCommands.auth;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.AuthedUser;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.Registry;
import org.jooq.Record;


@ConCMD
public class FDrop extends ConsoleCommand {

    public FDrop() {
        super(GeneralUtils.toArray("fdrop"), "fdrop [user]", "Forcefully drops a user");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) {
        for (String net : Registry.NetworkBot.keySet()) {
            if (PermUtils.isAccountEnabled(Registry.NetworkBot.get(net))) {
                AuthedUser authedUser = PermUtils.getAuthedUser(Registry.NetworkBot.get(net), args[0]);
                if (authedUser != null) {
                    Registry.AuthedUsers.remove(authedUser);
                    DatabaseUtils.removeNetworkUserPropertyByUser(net, authedUser.getAuthAccount());
                    DatabaseUtils.removeChannelUserPropertyByUser(net, authedUser.getAuthAccount());
                }
            }
        }
        Record account = DatabaseUtils.getAccount(args[0]);
        if (account != null) {
            DatabaseUtils.removeAccount(args[0]);
            commandIO.getPrintStream().println("Account dropped");
        } else {
            commandIO.getPrintStream().println("Account does not exist");
        }
    }
}
