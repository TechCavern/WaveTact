package com.techcavern.wavetact.commands.auth;

import com.techcavern.wavetact.annot.AuthCMD;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.objects.AuthedUser;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


public class Logout extends GenericCommand {
    @CMD
    @AuthCMD
    public Logout() {
        super(GeneralUtils.toArray("logout"), 0, "logout", "logs out a user");
    }
    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        AuthedUser authedUser = PermUtils.getAuthUser(Bot, user.getNick());
        if(authedUser == null){
            user.send().notice("Error, you are not logged in");
        }else{
            GeneralRegistry.AuthedUsers.remove(authedUser);
            IRCUtils.SendMessage(user, channel, "You are now logged out", isPrivate);
        }
        }
    }

