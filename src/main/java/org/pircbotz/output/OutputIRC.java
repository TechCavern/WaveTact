package org.pircbotz.output;

import org.pircbotz.PircBotZ;
import org.apache.commons.lang3.Validate;

public class OutputIRC {

    private final PircBotZ bot;

    public OutputIRC(PircBotZ bot) {
        this.bot = bot;
    }

    public void joinChannel(String channel) {
        Validate.notBlank(channel, "Channel '%s' is blank", channel);
        bot.sendRaw().rawLine("JOIN " + channel);
    }

    public void joinChannel(String channel, String key) {
        Validate.notBlank(channel, "Channel '%s' is blank", channel);
        Validate.notNull(key, "Key for channel %s cannot be null", channel);
        joinChannel(channel + " " + key);
    }

    void partChannel(String channel) {
        Validate.notBlank(channel, "Channel '%s' is blank", channel);
        bot.sendRaw().rawLine("PART " + channel);
    }

    public void partChannel(String channel, String message) {
        Validate.notBlank(channel, "Channel '%s' is blank", channel);
        Validate.notNull(message, "Message cannot be null");
        partChannel(channel + " " + message);
    }

    public void quitServer() {
        quitServer("");
    }

    void quitServer(String reason) {
        Validate.notNull(reason, "Reason cannot be null");
        bot.sendRaw().rawLine("QUIT :" + reason);
    }

    public void ctcpCommand(String target, String command) {
        Validate.notBlank(target, "Target '%s' is blank", target, command);
        Validate.notBlank(command, "CTCP command '%s' is blank", command, target);
        bot.sendRaw().rawLineSplit("PRIVMSG " + target + " :\u0001", command, "\u0001");
    }

    public void ctcpResponse(String target, String message) {
        Validate.notBlank(target, "Target '%s' is blank", target);
        bot.sendRaw().rawLine("NOTICE " + target + " :\u0001" + message + "\u0001");
    }

    public void message(String target, String message) {
        Validate.notBlank(target, "Target '%s' is blank", target);
        bot.sendRaw().rawLineSplit("PRIVMSG " + target + " :", message);
    }

    public void action(String target, String action) {
        Validate.notBlank(target, "Target '%s' is blank", target);
        ctcpCommand(target, "ACTION " + action);
    }

    public void notice(String target, String notice) {
        Validate.notBlank(target, "Target '%s' is blank", target);
        bot.sendRaw().rawLineSplit("NOTICE " + target + " :", notice);
    }

    public void changeNick(String newNick) {
        Validate.notBlank(newNick, "Nick '%s' is blank", newNick);
        bot.sendRaw().rawLine("NICK " + newNick);
    }

    public void invite(String nick, String channel) {
        Validate.notBlank(nick, "Nick '%s' is blank", nick);
        Validate.notBlank(channel, "Channel '%s' is blank", channel);
        bot.sendRaw().rawLine("INVITE " + nick + " :" + channel);
    }

    public void listChannels() {
        listChannels("");
    }

    void listChannels(String parameters) {
        Validate.notNull(parameters, "Parameters cannot be null");
        if (!bot.getInputParser().isChannelListRunning()) {
            bot.sendRaw().rawLine("LIST " + parameters);
        }
    }

    public void identify(final String password) {
        Validate.notBlank(password, "Password '%s' is blank", password);
        bot.sendRaw().rawLine("NICKSERV IDENTIFY " + password);
    }

    public void mode(String target, String mode) {
        bot.sendRaw().rawLine("MODE " + target + " " + mode);
    }
}
