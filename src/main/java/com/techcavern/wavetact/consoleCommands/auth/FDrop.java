package com.techcavern.wavetact.consoleCommands.auth;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.*;
import com.techcavern.wavetact.utils.*;
import org.jooq.Record;
import static com.techcavern.wavetactdb.Tables.*;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


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
            DatabaseUtils.deleteAccount(args[0]);
            commandIO.getPrintStream().println("Account dropped");
        } else {
            commandIO.getPrintStream().println("Account does not exist");
        }
    }
}
