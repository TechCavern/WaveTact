package com.techcavern.wavetact.consoleCommands.auth;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import static com.techcavern.wavetactdb.Tables.*;
import org.jooq.Record;
import java.util.UUID;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
@ConCMD
public class ResetPass extends ConsoleCommand {

    public ResetPass() {
        super(GeneralUtils.toArray("resetpassword resetpass"), "setpass [user] [newpass]", "Resets password");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception {
        Record account = DatabaseUtils.getAccount(args[0]);
        if (account != null) {
            account.setValue(ACCOUNTS.RANDOMSTRING, UUID.randomUUID().toString());
            account.setValue(ACCOUNTS.PASSWORD, Registry.encryptor.encryptPassword(args[1] + ACCOUNTS.RANDOMSTRING));
            commandIO.getPrintStream().println("Account dropped");
        } else {
            commandIO.getPrintStream().println("Account does not exist");
        }

    }
}

