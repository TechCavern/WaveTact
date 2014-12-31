package com.techcavern.wavetact.objects;

public abstract class ConsoleCommand {

    private final String[] comID;
    private final String desc;
    private final String syntax;

    protected ConsoleCommand(String[] comID, String syntax, String desc) {
        this.comID = comID;
        this.desc = desc;
        this.syntax = syntax;
    }

    public String[] getCommandID() {
        return this.comID;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getCommand() {
        return comID[0];
    }

    public String getSyntax() {
        if (syntax != null) {
            return syntax;
        } else {
            return "";
        }
    }

    public abstract void onCommand(String[] args, CommandIO commandIO) throws Exception;

}





