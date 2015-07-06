package com.techcavern.wavetact.consoleCommands.auth;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;
import org.jooq.Record;

import java.util.UUID;

import static com.techcavern.wavetactdb.Tables.ACCOUNTS;

@ConCMD
public class ResetPass extends ConsoleCommand {

    public ResetPass() {
        super(GeneralUtils.toArray("resetpassword resetpass"), "resetpass [user] [newpass]", "Resets password");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        Record account = DatabaseUtils.getAccount(args[0]);
        if (account != null) {
            account.setValue(ACCOUNTS.RANDOMSTRING, UUID.randomUUID().toString());
            account.setValue(ACCOUNTS.PASSWORD, Registry.encryptor.encryptPassword(args[1] + ACCOUNTS.RANDOMSTRING));
            DatabaseUtils.updateAccount(account);
            commandIO.getPrintStream().println("Password Reset");
        } else {
            commandIO.getPrintStream().println("Account does not exist");
        }

    }
}

