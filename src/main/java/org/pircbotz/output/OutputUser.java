package org.pircbotz.output;

import java.io.File;
import java.io.IOException;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.dcc.SendChat;
import org.pircbotz.dcc.SendFileTransfer;
import org.pircbotz.exception.DccException;

public class OutputUser {

    private final PircBotZ bot;
    private final User user;

    public OutputUser(PircBotZ bot, User user) {
        this.bot = bot;
        this.user = user;
    }

    public void invite(String channel) {
        bot.sendIRC().invite(user.getNick(), channel);
    }

    public void invite(Channel channel) {
        bot.sendIRC().invite(user.getNick(), channel.getName());
    }

    public void notice(String notice) {
        bot.sendIRC().notice(user.getNick(), notice);
    }

    public void action(String action) {
        bot.sendIRC().action(user.getNick(), action);
    }

    public void message(String message) {
        bot.sendIRC().message(user.getNick(), message);
    }

    public void ctcpCommand(String command) {
        bot.sendIRC().ctcpCommand(user.getNick(), command);
    }

    public void ctcpResponse(String message) {
        bot.sendIRC().ctcpResponse(user.getNick(), message);
    }

    public void mode(String mode) {
        bot.sendIRC().mode(user.getNick(), mode);
    }

    public SendFileTransfer dccFile(File file) throws IOException, DccException, InterruptedException {
        return bot.getDccHandler().sendFile(file, user);
    }

    public SendFileTransfer dccFile(File file, boolean passive) throws IOException, DccException, InterruptedException {
        return bot.getDccHandler().sendFile(file, user, passive);
    }

    public SendChat dccChat() throws IOException, InterruptedException {
        return bot.getDccHandler().sendChat(user);
    }

    public SendChat dccChat(boolean passive) throws IOException, InterruptedException {
        return bot.getDccHandler().sendChat(user, passive);
    }
}
