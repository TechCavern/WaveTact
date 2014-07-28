package org.pircbotz.generics;

import org.pircbotz.Channel;

public interface GenericChannelEvent extends GenericEvent {

    public Channel getChannel();
}
