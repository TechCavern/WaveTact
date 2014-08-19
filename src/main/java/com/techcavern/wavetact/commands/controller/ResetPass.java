package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.AuthCMD;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.AccountUtils;
import com.techcavern.wavetact.utils.objects.Account;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


public class ResetPass extends GenericCommand {
    @CMD
    @ConCMD
    public ResetPass() {
        super(GeneralUtils.toArray("resetpassword resetpass"), 9001, "setpass [user] [newpass]", "resets Password");
    }
    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if(!PermUtils.checkIfAccountEnabled(Bot)){
            IRCUtils.sendError(user, "This network is set to " + GetUtils.getAuthType(Bot) + " Authentication");
            return;
        }
        Account acc = AccountUtils.getAccount(args[0]);
        if(acc != null) {
            acc.setAuthPassword(GeneralRegistry.encryptor.encryptPassword(args[1]));
            AccountUtils.saveAccounts();
            IRCUtils.SendMessage(user, channel, "Password Changed Successfully", isPrivate);
        }else{
            IRCUtils.sendError(user, "User does not exist");
        }

        }
    }

