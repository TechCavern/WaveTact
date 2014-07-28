package org.pircbotz.hooks.events;

import java.util.List;
import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.Event;

public class WhoisEvent extends Event {

    private final String nick;
    private final String login;
    private final String hostname;
    private final String realname;
    private final List<String> channels;
    private final String server;
    private final String serverInfo;
    private final long idleSeconds;
    private final long signOnTime;
    private final String registeredAs;

    public WhoisEvent(PircBotZ bot, Builder builder) {
        super(bot);
        this.nick = builder.getNick();
        this.login = builder.getLogin();
        this.hostname = builder.getHostname();
        this.realname = builder.getRealname();
        this.channels = builder.getChannels();
        this.server = builder.getServer();
        this.serverInfo = builder.getServerInfo();
        this.idleSeconds = builder.getIdleSeconds();
        this.signOnTime = builder.getSignOnTime();
        this.registeredAs = builder.getRegisteredAs();
    }

    @Override
    public void respond(String response) {
        getBot().sendIRC().message(getNick(), response);
    }

    public static class Builder {

        private String nick;
        private String login;
        private String hostname;
        private String realname;
        private List<String> channels;
        private String server;
        private String serverInfo;
        private long idleSeconds;
        private long signOnTime;
        private String registeredAs;

        public WhoisEvent generateEvent(PircBotZ bot) {
            return new WhoisEvent(bot, this);
        }

        public String getNick() {
            return nick;
        }

        public String getLogin() {
            return login;
        }

        public String getHostname() {
            return hostname;
        }

        public String getRealname() {
            return realname;
        }

        public List<String> getChannels() {
            return channels;
        }

        public String getServer() {
            return server;
        }

        public String getServerInfo() {
            return serverInfo;
        }

        public long getIdleSeconds() {
            return idleSeconds;
        }

        public long getSignOnTime() {
            return signOnTime;
        }

        public String getRegisteredAs() {
            return registeredAs;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public void setChannels(List<String> channels) {
            this.channels = channels;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public void setServerInfo(String serverInfo) {
            this.serverInfo = serverInfo;
        }

        public void setIdleSeconds(long idleSeconds) {
            this.idleSeconds = idleSeconds;
        }

        public void setSignOnTime(long signOnTime) {
            this.signOnTime = signOnTime;
        }

        public void setRegisteredAs(String registeredAs) {
            this.registeredAs = registeredAs;
        }
    }

    public String getNick() {
        return nick;
    }

    public String getLogin() {
        return login;
    }

    public String getHostname() {
        return hostname;
    }

    public String getRealname() {
        return realname;
    }

    public List<String> getChannels() {
        return channels;
    }

    public String getServer() {
        return server;
    }

    public String getServerInfo() {
        return serverInfo;
    }

    public long getIdleSeconds() {
        return idleSeconds;
    }

    public long getSignOnTime() {
        return signOnTime;
    }

    public String getRegisteredAs() {
        return registeredAs;
    }
}
