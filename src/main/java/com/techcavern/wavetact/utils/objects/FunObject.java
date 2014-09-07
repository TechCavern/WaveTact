package com.techcavern.wavetact.utils.objects;


public class FunObject {

    private final String Action;
    private final boolean MessageExists;
    private final String Message;

    public FunObject(String Action, boolean MessageExists, String Message) {
        this.Action = Action;
        this.MessageExists = MessageExists;
        this.Message = Message;
    }

    public String getAction() {
        return this.Action;
    }

    public String getMessage() {
        return this.Message;
    }

    public boolean getMessageExists() {
        return MessageExists;
    }
}