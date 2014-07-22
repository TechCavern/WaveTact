package com.techcavern.wavetact.utils.objects;

public abstract class CommandLine {

    private final String[] argument;
    private final String helpString;
    private final boolean isPriority;

    protected CommandLine(String[] stringArray, String helpString, boolean isPriority) {
        this.argument = stringArray;
        this.helpString = helpString;
        this.isPriority = isPriority;

    }

    public boolean getIsPriority(){
        return isPriority;
    }


    public String[] getArgument() {
        return this.argument;
    }

    public String getHelpString() {
        return this.helpString;
    }

    public abstract void doAction(String[] args);

}





