package org.pircbotz.snapshot;

import com.google.common.collect.Multimap;
import org.pircbotz.UserChannelMap;

public class UserChannelMapSnapshot extends UserChannelMap<UserSnapshot, ChannelSnapshot> {

    public UserChannelMapSnapshot(Multimap<UserSnapshot, ChannelSnapshot> userToChannelSnapshot, Multimap<ChannelSnapshot, UserSnapshot> channelToUserSnapshot) {
        super(userToChannelSnapshot, channelToUserSnapshot);
    }

    @Override
    public void removeUserFromChannel(UserSnapshot user, ChannelSnapshot channel) {
        throw new UnsupportedOperationException("Attempting to remove user from a channel in a snapshot");
    }

    @Override
    public void removeUser(UserSnapshot user) {
        throw new UnsupportedOperationException("Attempting to remove user from snapshot");
    }

    @Override
    public void removeChannel(ChannelSnapshot channel) {
        throw new UnsupportedOperationException("Attempting to remove channel from snapshot");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Attempting to clear snapshot");
    }
}
