package com.techcavern.wavetact.consoleCommands.auth;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.*;
import com.techcavern.wavetact.utils.*;
import org.jooq.Record;


@ConCMD
public class FDrop extends ConsoleCommand {

    public FDrop() {
        super(GeneralUtils.toArray("fdrop"), "fdrop [user]", "Forcefully drops a user");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) {
        for(NetProperty e:Registry.NetworkName){
            AuthedUser authedUser = PermUtils.getAuthedUser(e.getBot(), args[0]);
            if (authedUser != null) {
                Registry.AuthedUsers.remove(authedUser);
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
