package org.pircbotz.generics;

import org.pircbotz.User;

public interface GenericUserModeEvent extends GenericChannelUserEvent {

    public User getRecipient();
}
