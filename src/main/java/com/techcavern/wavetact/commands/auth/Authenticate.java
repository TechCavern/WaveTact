package com.techcavern.wavetact.commands.auth;

import com.techcavern.wavetact.annot.AuthCMD;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.AccountUtils;
import com.techcavern.wavetact.utils.objects.Account;
import com.techcavern.wavetact.utils.objects.AuthedUser;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


public class Authenticate extends GenericCommand {
    @CMD
    @AuthCMD
    public Authenticate() {
        super(GeneralUtils.toArray("authenticate auth identify id login"), 0, "identify (username) [password]", "identifies a user");
    }
    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if(!PermUtils.checkIfAccountEnabled(Bot)){
            user.send().notice("This network is set to " + GetUtils.getAuthType(Bot) + " Authentication");
            return;
        }
        String userString;
        String password;
        if(args.length < 2){
            userString = user.getNick();
            password = args[0];
        }else{
            userString = args[0];
            password = args[1];
        }
        if(PermUtils.getAuthedAccount(Bot, user.getNick()) != null){
            user.send().notice("Error, you are already identified");
        }else{
            Account acc = AccountUtils.getAccount(userString);
            if(acc != null && GeneralRegistry.encryptor.checkPassword(password, acc.getAuthPassword())){
                    GeneralRegistry.AuthedUsers.add(new AuthedUser(Bot.getServerInfo().getServerName(), userString, IRCUtils.getHostmask(Bot, user.getNick(), false)));
                    IRCUtils.SendMessage(user, channel, "Identification Successful", isPrivate);
            }else{
                user.send().notice("Unable to identify (Incorrect User/Password Combination)");
            }
        }
        }
    }

