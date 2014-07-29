package org.pircbotz.output;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.ListenerAdapter;
import org.pircbotz.hooks.events.PartEvent;
import org.apache.commons.lang3.StringUtils;

public class OutputChannel {

    private final PircBotZ bot;
    private final Channel channel;

    public OutputChannel(PircBotZ bot, Channel channel) {
        this.bot = bot;
        this.channel = channel;
    }

    public void part() {
        bot.sendRaw().rawLine("PART " + channel.getName());
    }

    public void part(String reason) {
        bot.sendRaw().rawLine("PART " + channel.getName() + " :" + reason);
    }

    public void message(String message) {
        bot.sendIRC().message(channel.getName(), message);
    }

    public void message(User user, String message) {
        if (user == null) {
            throw new IllegalArgumentException("Can't send message to null user");
        }
        message(user.getNick() + ": " + message);
    }

    public void action(String action) {
        bot.sendIRC().action(channel.getName(), action);
    }

    public void notice(String notice) {
        bot.sendIRC().notice(channel.getName(), notice);
    }

    public void invite(Channel otherChannel) {
        if (otherChannel == null) {
            throw new IllegalArgumentException("Can't send invite to null invite channel");
        }
        bot.sendIRC().invite(channel.getName(), otherChannel.getName());
    }

    public void ctcpCommand(String command) {
        bot.sendIRC().ctcpCommand(channel.getName(), command);
    }

    public void cycle() {
        cycle("");
    }

    public void cycle(final String key) {
        final String channelName = channel.getName();
        bot.getConfiguration().getListenerManager().addListener(new ListenerAdapter() {
            @Override
            public void onPart(PartEvent event) throws Exception {
                if (event.getBot() == bot) {
                    bot.sendIRC().joinChannel(channelName, key);
                    bot.getConfiguration().getListenerManager().removeListener(this);
                }
            }
        });
        part();
    }

    public void who() {
        bot.sendRaw().rawLine("WHO " + channel.getName());
    }

    public void getMode() {
        bot.sendRaw().rawLine("MODE " + channel.getName());
    }

    public void setMode(String mode) {
        if (mode == null) {
            throw new IllegalArgumentException("Can't set mode on channel to null");
        }
        bot.sendIRC().mode(channel.getName(), mode);
    }

    public void setMode(String mode, Object... args) {
        if (mode == null) {
            throw new IllegalArgumentException("Can't set mode on channel to null");
        }
        if (args == null) {
            throw new IllegalArgumentException("Can't set mode arguments to null");
        }
        setMode(mode + StringUtils.join(args, " "));
    }

    public void setMode(String mode, User user) {
        if (mode == null) {
            throw new IllegalArgumentException("Can't set user mode on channel to null");
        }
        if (user == null) {
            throw new IllegalArgumentException("Can't set user mode on null user");
        }
        setMode(mode + user.getNick());
    }

    public void setChannelLimit(int limit) {
        setMode("+l", limit);
    }

    public void removeChannelLimit() {
        setMode("-l");
    }

    public void setChannelKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Can't set channel key to null");
        }
        setMode("+k", key);
    }

    public void removeChannelKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Can't remove channel key with null key");
        }
        setMode("-k", key);
    }

    public void setInviteOnly() {
        setMode("+i");
    }

    public void removeInviteOnly() {
        setMode("-i");
    }

    public void setModerated() {
        setMode("+m");
    }

    public void removeModerated() {
        setMode("-m");
    }

    public void setNoExternalMessages() {
        setMode("+n");
    }

    public void removeNoExternalMessages() {
        setMode("-n");
    }

    public void setSecret() {
        setMode("+s");
    }

    public void removeSecret() {
        setMode("-s");
    }

    public void setTopicProtection() {
        setMode("+t");
    }

    public void removeTopicProtection() {
        setMode("-t");
    }

    public void setChannelPrivate() {
        setMode("+p");
    }

    public void removeChannelPrivate() {
        setMode("-p");
    }

    public void ban(String hostmask) {
        if (hostmask == null) {
            throw new IllegalArgumentException("Can't set ban on null hostmask");
        }
        bot.sendRaw().rawLine("MODE " + channel.getName() + " +b " + hostmask);
    }

    public void unBan(String hostmask) {
        if (hostmask == null) {
            throw new IllegalArgumentException("Can't remove ban on null hostmask");
        }
        bot.sendRaw().rawLine("MODE " + channel.getName() + " -b " + hostmask);
    }

    public void op(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can't set op on null user");
        }
        setMode("+o " + user.getNick());
    }

    public void deOp(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can't remove op on null user");
        }
        setMode("-o " + user.getNick());
    }

    public void voice(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can't set voice on null user");
        }
        setMode("+v " + user.getNick());
    }

    public void deVoice(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can't remove voice on null user");
        }
        setMode("-v " + user.getNick());
    }

    public void halfOp(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can't set halfop on null user");
        }
        setMode("+h " + user.getNick());
    }

    public void deHalfOp(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can't remove halfop on null user");
        }
        setMode("-h " + user.getNick());
    }

    public void owner(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can't set owner on null user");
        }
        setMode("+q " + user.getNick());
    }

    public void deOwner(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can't remove owner on null user");
        }
        setMode("-q " + user.getNick());
    }

    public void superOp(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can't set super op on null user");
        }
        setMode("+a " + user.getNick());
    }

    public void deSuperOp(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can't remove super op on null user");
        }
        setMode("-a " + user.getNick());
    }

    public void setTopic(String topic) {
        if (topic == null) {
            throw new IllegalArgumentException("Can't set topic to null");
        }
        bot.sendRaw().rawLine("TOPIC " + channel.getName() + " :" + topic);
    }

    public void kick(User user) {
        kick(user, "");
    }

    public void kick(User user, String reason) {
        if (user == null) {
            throw new IllegalArgumentException("Can't kick null user");
        }
        bot.sendRaw().rawLine("KICK " + channel.getName() + " " + user.getNick() + " :" + reason);
    }
}
