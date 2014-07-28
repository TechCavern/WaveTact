package org.pircbotz.generics;

import org.pircbotz.User;

public interface GenericUserEvent extends GenericEvent {

    public User getUser();
}
