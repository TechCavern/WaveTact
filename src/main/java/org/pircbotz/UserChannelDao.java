package org.pircbotz;

import com.google.common.collect.ImmutableMap;
import java.io.Closeable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.pircbotz.snapshot.ChannelSnapshot;
import org.pircbotz.snapshot.UserChannelDaoSnapshot;
import org.pircbotz.snapshot.UserChannelMapSnapshot;
import org.pircbotz.snapshot.UserSnapshot;
import org.apache.commons.lang3.Validate;

public class UserChannelDao<P extends PircBotZ, U extends User, C extends Channel> implements Closeable {

    private final P bot;
    private final BotFactory botFactory;
    private final Locale locale;
    private final Object accessLock = new Object();
    private final UserChannelMap<U, C> mainMap;
    private final EnumMap<UserLevel, UserChannelMap<U, C>> levelsMap;
    private final Map<String, U> userNickMap;
    private final Map<String, C> channelNameMap;
    private final Set<U> privateUsers;
    private final Class<P> botClass;
    private final Class<U> userClass;
    private final Class<C> channelClass;

    public UserChannelDao(P bot, BotFactory botFactory, Class<P> botClass, Class<U> userClass, Class<C> channelClass) {
        this(bot, botFactory, bot.getConfiguration().getLocale(), new UserChannelMap<>(), new EnumMap<>(UserLevel.class), new HashMap<>(), new HashMap<>(), new HashSet<>(), botClass, userClass, channelClass);
        for (UserLevel level : UserLevel.values()) {
            levelsMap.put(level, new UserChannelMap<>());
        }
    }

    protected UserChannelDao(P bot, BotFactory botFactory, Locale locale, UserChannelMap<U, C> mainMap, EnumMap<UserLevel, UserChannelMap<U, C>> levelsMap, Map<String, U> userNickMap, Map<String, C> channelNameMap, Set<U> privateUsers, Class<P> botClass, Class<U> userClass, Class<C> channelClass) {
        this.bot = bot;
        this.botFactory = botFactory;
        this.locale = locale;
        this.mainMap = mainMap;
        this.levelsMap = levelsMap;
        this.userNickMap = userNickMap;
        this.channelNameMap = channelNameMap;
        this.privateUsers = privateUsers;
        this.botClass = botClass;
        this.userClass = userClass;
        this.channelClass = channelClass;
    }

    public U getUser(String nick) {
        Validate.notBlank(nick, "Cannot get a blank user");
        U user = userNickMap.get(nick.toLowerCase(locale));
        if (user != null) {
            return user;
        }
        if (botFactory == null) {
            throw new UnsupportedOperationException("Dao cannot create new user");
        }
        try {
            user = userClass.cast(botFactory.createUser(bot, nick));
            userNickMap.put(nick.toLowerCase(locale), user);
            return user;
        } catch (ClassCastException e) {
            if (user != null) {
                throw new UnsupportedOperationException("Dao stores " + user.getClass().getSimpleName() + " however cannot be cast to " + userClass.getSimpleName());
            }
            throw new UnsupportedOperationException("Dao stores a null user and cannot create new " + userClass.getSimpleName());
        }
    }

    public boolean userExists(String nick) {
        return userNickMap.containsKey(nick.toLowerCase(locale));
    }

    public Set<U> getAllUsers() {
        return new HashSet<>(userNickMap.values());
    }

    protected void addUserToChannel(U user, C channel) {
        mainMap.addUserToChannel(user, channel);
    }

    protected void addUserToPrivate(U user) {
        privateUsers.add(user);
    }

    protected void addUserToLevel(UserLevel level, U user, C channel) {
        levelsMap.get(level).addUserToChannel(user, channel);
    }

    protected void removeUserFromLevel(UserLevel level, U user, C channel) {
        levelsMap.get(level).removeUserFromChannel(user, channel);
    }

    public Set<U> getNormalUsers(C channel) {
        Set<U> remainingUsers = new HashSet<>(mainMap.getUsers(channel));
        for (UserChannelMap<U, C> curLevelMap : levelsMap.values()) {
            remainingUsers.removeAll(curLevelMap.getUsers(channel));
        }
        return new HashSet<>(remainingUsers);
    }

    public Set<U> getUsers(C channel, UserLevel level) {
        return levelsMap.get(level).getUsers(channel);
    }

    public Set<UserLevel> getLevels(C channel, U user) {
        Set<UserLevel> builder = new HashSet<>();
        for (Map.Entry<UserLevel, UserChannelMap<U, C>> curEntry : levelsMap.entrySet()) {
            if (curEntry.getValue().containsEntry(user, channel)) {
                builder.add(curEntry.getKey());
            }
        }
        return builder;
    }

    public Set<C> getNormalUserChannels(U user) {
        Set<C> remainingChannels = new HashSet<>(mainMap.getChannels(user));
        for (UserChannelMap<U, C> curLevelMap : levelsMap.values()) {
            remainingChannels.removeAll(curLevelMap.getChannels(user));
        }
        return new HashSet<>(remainingChannels);
    }

    public Set<C> getChannels(U user, UserLevel level) {
        return levelsMap.get(level).getChannels(user);
    }

    protected void removeUserFromChannel(U user, C channel) {
        mainMap.removeUserFromChannel(user, channel);
        for (UserChannelMap<U, C> curLevelMap : levelsMap.values()) {
            curLevelMap.removeUserFromChannel(user, channel);
        }
        if (!privateUsers.contains(user) && !mainMap.containsUser(user)) {
            Set<String> keySet = userNickMap.keySet();
            List<String> names = new LinkedList<>();
            for (String key : keySet) {
                if (userNickMap.get(key) == user) {
                    names.add(key);
                }
            }
            for (String name : names) {
                userNickMap.remove(name);
            }
        }
    }

    protected void removeUser(U user) {
        mainMap.removeUser(user);
        for (UserChannelMap<U, C> curLevelMap : levelsMap.values()) {
            curLevelMap.removeUser(user);
        }
        Set<String> keySet = userNickMap.keySet();
        List<String> names = new LinkedList<>();
        for (String key : keySet) {
            if (userNickMap.get(key) == user) {
                names.add(key);
            }
        }
        for (String name : names) {
            userNickMap.remove(name);
        }
        privateUsers.remove(user);
    }

    boolean levelContainsUser(UserLevel level, C channel, U user) {
        return levelsMap.get(level).containsEntry(user, channel);
    }

    protected void renameUser(U user, String newNick) {
        user.setNick(newNick);
        Set<String> keySet = userNickMap.keySet();
        List<String> names = new LinkedList<>();
        for (String key : keySet) {
            if (userNickMap.get(key) == user) {
                names.add(key);
            }
        }
        for (String name : names) {
            userNickMap.remove(name);
        }
        userNickMap.put(newNick.toLowerCase(locale), user);
    }

    public C getChannel(String name) {
        Validate.notBlank(name, "Cannot get a blank channel");
        C chan = channelNameMap.get(name.toLowerCase(locale));
        if (chan != null) {
            return chan;
        }
        if (botFactory == null) {
            throw new UnsupportedOperationException("Dao cannot create new channel");
        }
        try {
            chan = channelClass.cast(botFactory.createChannel(bot, name));
            channelNameMap.put(name.toLowerCase(locale), chan);
            return chan;
        } catch (ClassCastException e) {
            if (chan != null) {
                throw new UnsupportedOperationException("Dao stores " + chan.getClass().getSimpleName() + " however cannot be cast to " + channelClass.getSimpleName());
            }
            throw new UnsupportedOperationException("Dao stores a null channel and cannot create new " + channelClass.getSimpleName());
        }
    }

    public boolean channelExists(String name) {
        return channelNameMap.containsKey(name.toLowerCase(locale));
    }

    public Set<U> getUsers(C channel) {
        return mainMap.getUsers(channel);
    }

    public Set<C> getAllChannels() {
        return new HashSet<>(channelNameMap.values());
    }

    public Set<C> getChannels(U user) {
        return mainMap.getChannels(user);
    }

    protected void removeChannel(C channel) {
        mainMap.removeChannel(channel);
        for (UserChannelMap<U, C> curLevelMap : levelsMap.values()) {
            curLevelMap.removeChannel(channel);
        }
        Set<String> keySet = channelNameMap.keySet();
        for (String key : keySet) {
            if (channelNameMap.get(key) == channel) {
                channelNameMap.remove(key);
            }
        }
    }

    @Override
    public void close() {
        mainMap.clear();
        for (UserChannelMap<U, C> curLevelMap : levelsMap.values()) {
            curLevelMap.clear();
        }
        channelNameMap.clear();
        privateUsers.clear();
        userNickMap.clear();
    }

    public UserChannelDaoSnapshot<P> createSnapshot() {
        ImmutableMap.Builder<User, UserSnapshot> userSnapshotBuilder = ImmutableMap.builder();
        for (User curUser : userNickMap.values()) {
            userSnapshotBuilder.put(curUser, curUser.createSnapshot());
        }
        ImmutableMap<User, UserSnapshot> userSnapshotMap = userSnapshotBuilder.build();

        ImmutableMap.Builder<Channel, ChannelSnapshot> channelSnapshotBuilder = ImmutableMap.builder();
        for (Channel curChannel : channelNameMap.values()) {
            channelSnapshotBuilder.put(curChannel, curChannel.createSnapshot());
        }
        ImmutableMap<Channel, ChannelSnapshot> channelSnapshotMap = channelSnapshotBuilder.build();

        UserChannelMapSnapshot mainMapSnapshot = mainMap.createSnapshot(userSnapshotMap, channelSnapshotMap);
        EnumMap<UserLevel, UserChannelMap<UserSnapshot, ChannelSnapshot>> levelsMapSnapshot = new EnumMap<>(UserLevel.class);
        for (Map.Entry<UserLevel, UserChannelMap<U, C>> curLevel : levelsMap.entrySet()) {
            levelsMapSnapshot.put(curLevel.getKey(), curLevel.getValue().createSnapshot(userSnapshotMap, channelSnapshotMap));
        }
        Map<String, UserSnapshot> userNickMapSnapshotBuilder = new HashMap<>();
        for (Map.Entry<String, U> curNick : userNickMap.entrySet()) {
            userNickMapSnapshotBuilder.put(curNick.getKey(), curNick.getValue().createSnapshot());
        }
        Map<String, ChannelSnapshot> channelNameMapSnapshotBuilder = new HashMap<>();
        for (Map.Entry<String, C> curName : channelNameMap.entrySet()) {
            channelNameMapSnapshotBuilder.put(curName.getKey(), curName.getValue().createSnapshot());
        }
        Set<UserSnapshot> privateUserSnapshotBuilder = new HashSet<>();
        for (User curUser : privateUsers) {
            privateUserSnapshotBuilder.add(curUser.createSnapshot());
        }
        UserChannelDaoSnapshot<P> daoSnapshot = new UserChannelDaoSnapshot<>(
                bot,
                locale,
                mainMapSnapshot,
                levelsMapSnapshot,
                new ImmutableMap.Builder<String, UserSnapshot>().putAll(userNickMapSnapshotBuilder).build(),
                new ImmutableMap.Builder<String, ChannelSnapshot>().putAll(channelNameMapSnapshotBuilder).build(),
                privateUserSnapshotBuilder,
                botClass);
        for (UserSnapshot curUserSnapshot : userSnapshotMap.values()) {
            curUserSnapshot.setDao(daoSnapshot);
        }
        for (ChannelSnapshot curChannelSnapshot : channelSnapshotMap.values()) {
            curChannelSnapshot.setDao(daoSnapshot);
        }
        return daoSnapshot;
    }

    protected Map<String, U> getUserNickMap() {
        return userNickMap;
    }

    protected Map<String, C> getChannelNameMap() {
        return channelNameMap;
    }
}
