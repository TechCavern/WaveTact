package com.techcavern.wavetact.api;

import org.pircbotx.hooks.events.MessageEvent;

public abstract class Command {
    private String name;
    private int permissionLevel;

    protected Command(String name, int permissionLevel) {
        this.name = name.toLowerCase();
        this.permissionLevel = permissionLevel;
    }

    public abstract void onCommand(MessageEvent<?> event, String... args) throws Exception;

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public String getName() {
        return name;
    }
}
