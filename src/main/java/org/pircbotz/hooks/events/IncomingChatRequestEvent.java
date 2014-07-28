package org.pircbotz.hooks.events;

import java.io.IOException;
import java.net.InetAddress;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.dcc.ReceiveChat;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericDCCEvent;

public class IncomingChatRequestEvent extends Event implements GenericDCCEvent {

    private final User user;
    private final InetAddress chatAddress;
    private final int chatPort;
    private final String chatToken;
    private final boolean passive;

    public IncomingChatRequestEvent(PircBotZ bot, User user, InetAddress chatAddress, int chatPort, String chatToken, boolean passive) {
        super(bot);
        this.user = user;
        this.chatAddress = chatAddress;
        this.chatPort = chatPort;
        this.chatToken = chatToken;
        this.passive = passive;
    }

    public ReceiveChat accept() throws IOException {
        return getBot().getDccHandler().acceptChatRequest(this);
    }

    @Override
    public void respond(String response) {
        getUser().send().message(response);
    }

    @Override
    public User getUser() {
        return user;
    }

    public InetAddress getChatAddress() {
        return chatAddress;
    }

    public int getChatPort() {
        return chatPort;
    }

    public String getChatToken() {
        return chatToken;
    }

    @Override
    public boolean isPassive() {
        return passive;
    }
}
