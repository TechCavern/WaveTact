package org.pircbotz.snapshot;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.pircbotz.PircBotZ;
import org.pircbotz.UserChannelDao;
import org.pircbotz.UserChannelMap;
import org.pircbotz.UserLevel;

public class UserChannelDaoSnapshot<P extends PircBotZ> extends UserChannelDao<P, UserSnapshot, ChannelSnapshot> {

    public UserChannelDaoSnapshot(P bot, Locale locale, UserChannelMapSnapshot mainMap, EnumMap<UserLevel, UserChannelMap<UserSnapshot, ChannelSnapshot>> levelsMap, Map<String, UserSnapshot> userNickMap, Map<String, ChannelSnapshot> channelNameMap, Set<UserSnapshot> privateUsers, Class<P> botClass) {
        super(bot, null, locale, mainMap, levelsMap, userNickMap, channelNameMap, privateUsers, botClass, UserSnapshot.class, ChannelSnapshot.class);
    }

    @Override
    public UserSnapshot getUser(String nick) {
        UserSnapshot user = getUserNickMap().get(nick.toLowerCase());
        if (user == null) {
            throw new NoSuchElementException("User " + nick + " does not exist");
        }
        return user;
    }

    @Override
    public ChannelSnapshot getChannel(String name) {
        ChannelSnapshot channel = getChannelNameMap().get(name.toLowerCase());
        if (channel == null) {
            throw new NoSuchElementException("Channel " + channel + " does not exist");
        }
        return channel;
    }

    @Override
    protected void removeUserFromChannel(UserSnapshot user, ChannelSnapshot channel) {
        throw new UnsupportedOperationException("Attempting to modify a snapshot object");
    }

    @Override
    protected void removeUser(UserSnapshot user) {
        throw new UnsupportedOperationException("Attempting to modify a snapshot object");
    }

    @Override
    protected void renameUser(UserSnapshot user, String newNick) {
        throw new UnsupportedOperationException("Attempting to modify a snapshot object");
    }

    @Override
    protected void removeChannel(ChannelSnapshot channel) {
        throw new UnsupportedOperationException("Attempting to modify a snapshot object");
    }

    @Override
    protected void addUserToChannel(UserSnapshot user, ChannelSnapshot channel) {
        throw new UnsupportedOperationException("Attempting to modify a snapshot object");
    }

    @Override
    protected void addUserToPrivate(UserSnapshot user) {
        throw new UnsupportedOperationException("Attempting to modify a snapshot object");
    }

    @Override
    protected void addUserToLevel(UserLevel level, UserSnapshot user, ChannelSnapshot channel) {
        throw new UnsupportedOperationException("Attempting to modify a snapshot object");
    }

    @Override
    protected void removeUserFromLevel(UserLevel level, UserSnapshot user, ChannelSnapshot channel) {
        throw new UnsupportedOperationException("Attempting to modify a snapshot object");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Attempting to modify a snapshot object");
    }
}
