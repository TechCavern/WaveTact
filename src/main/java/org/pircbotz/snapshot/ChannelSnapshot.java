package org.pircbotz.snapshot;

import java.util.List;
import java.util.Set;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.UserChannelDao;
import org.pircbotz.UserLevel;
import org.pircbotz.Utils;

public class ChannelSnapshot extends Channel {

    private UserChannelDaoSnapshot<? extends PircBotZ> dao;
    private final Channel generatedFrom;

    public ChannelSnapshot(Channel channel, String mode) {
        super(channel.getBot(), null, channel.getName());
        this.generatedFrom = channel;
        String mode1 = mode;
        super.setCreateTimestamp(channel.getCreateTimestamp());
        super.setTopic(channel.getTopic());
        super.setTopicSetter(channel.getTopicSetter());
        super.setTopicTimestamp(channel.getTopicTimestamp());
        super.setChannelKey(channel.getChannelKey());
        super.setChannelLimit(channel.getChannelLimit());
        super.setChannelPrivate(channel.isChannelPrivate());
        super.setInviteOnly(channel.isInviteOnly());
        super.setModerated(channel.isModerated());
        super.setNoExternalMessages(channel.isNoExternalMessages());
        super.setSecret(channel.isSecret());
        super.setTopicProtection(channel.hasTopicProtection());
    }

    UserChannelDaoSnapshot<? extends PircBotZ> getSnapshotDao() {
        return dao;
    }

    @Override
    public UserChannelDao<PircBotZ, User, Channel> getDao() {
        throw new UnsupportedOperationException("Cannot get Dao for a snapshot channel");
    }

    @Override
    public Set<UserLevel> getUserLevels(User user) {
        return getSnapshotDao().getLevels(this, user instanceof UserSnapshot ? (UserSnapshot) user : user.createSnapshot());
    }

    @Override
    public Set<User> getNormalUsers() {
        return Utils.castSet(getSnapshotDao().getNormalUsers(this), User.class);
    }

    @Override
    public Set<User> getOps() {
        return Utils.castSet(getSnapshotDao().getUsers(this, UserLevel.OP), User.class);
    }

    @Override
    public Set<User> getVoices() {
        return Utils.castSet(getSnapshotDao().getUsers(this, UserLevel.VOICE), User.class);
    }

    @Override
    public Set<User> getOwners() {
        return Utils.castSet(getSnapshotDao().getUsers(this, UserLevel.OWNER), User.class);
    }

    @Override
    public Set<User> getHalfOps() {
        return Utils.castSet(getSnapshotDao().getUsers(this, UserLevel.HALFOP), User.class);
    }

    @Override
    public Set<User> getSuperOps() {
        return Utils.castSet(getSnapshotDao().getUsers(this, UserLevel.SUPEROP), User.class);
    }

    @Override
    public Set<User> getUsers() {
        return Utils.castSet(getSnapshotDao().getUsers(this), User.class);
    }

    @Override
    public ChannelSnapshot createSnapshot() {
        throw new UnsupportedOperationException("Attempting to generate channel snapshot from a snapshot");
    }

    @Override
    public void setTopic(String topic) {
        throw new UnsupportedOperationException("Cannot change settings on snapshot");
    }

    @Override
    public void setTopicTimestamp(long topicTimestamp) {
        throw new UnsupportedOperationException("Cannot change settings on snapshot");
    }

    @Override
    public void setCreateTimestamp(long createTimestamp) {
        throw new UnsupportedOperationException("Cannot change settings on snapshot");
    }

    @Override
    public void setTopicSetter(String topicSetter) {
        throw new UnsupportedOperationException("Cannot change settings on snapshot");
    }

    @Override
    public void setModerated(boolean moderated) {
        throw new UnsupportedOperationException("Cannot change settings on snapshot");
    }

    @Override
    public void setNoExternalMessages(boolean noExternalMessages) {
        throw new UnsupportedOperationException("Cannot change settings on snapshot");
    }

    @Override
    public void setInviteOnly(boolean inviteOnly) {
        throw new UnsupportedOperationException("Cannot change settings on snapshot");
    }

    @Override
    public void setSecret(boolean secret) {
        throw new UnsupportedOperationException("Cannot change settings on snapshot");
    }

    @Override
    public void setChannelPrivate(boolean channelPrivate) {
        throw new UnsupportedOperationException("Cannot change settings on snapshot");
    }

    @Override
    public void setTopicProtection(boolean topicProtection) {
        throw new UnsupportedOperationException("Cannot change settings on snapshot");
    }

    @Override
    public void setChannelLimit(int channelLimit) {
        throw new UnsupportedOperationException("Cannot change settings on snapshot");
    }

    @Override
    public void setChannelKey(String channelKey) {
        throw new UnsupportedOperationException("Cannot change settings on snapshot");
    }

    @Override
    public void setMode(String mode, List<String> modeParsed) {
        throw new UnsupportedOperationException("Cannot change settings on snapshot");
    }

    public Channel getGeneratedFrom() {
        return generatedFrom;
    }

    public void setDao(UserChannelDaoSnapshot<? extends PircBotZ> dao) {
        this.dao = dao;
    }
}
