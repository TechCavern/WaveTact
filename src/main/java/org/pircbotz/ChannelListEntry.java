package org.pircbotz;

public class ChannelListEntry {

    private final String name;
    private final int users;
    private final String topic;

    public ChannelListEntry(String name, int users, String topic) {
        this.name = name;
        this.users = users;
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public int getUsers() {
        return users;
    }

    public String getTopic() {
        return topic;
    }
}
