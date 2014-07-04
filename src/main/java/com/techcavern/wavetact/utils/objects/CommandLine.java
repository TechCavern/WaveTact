package com.techcavern.wavetact.utils.objects;

import com.techcavern.wavetact.utils.GeneralRegistry;

public abstract class CommandLine {

    private final String[] argument;
    private final String helpString;
    private final boolean isPriority;

    protected CommandLine(String[] stringArray, String helpString, boolean isPriority) {
        this.argument = stringArray;
        this.helpString = helpString;
        this.isPriority = isPriority;
        create();

    }

    public void create() {
        if (this.isPriority) {
            GeneralRegistry.CommandLineArguments.add(this);
        } else {
            GeneralRegistry.CommandLines.add(this);
        }
    }


    public String[] getArgument() {
        return this.argument;
    }

    public String getHelpString() {
        return this.helpString;
    }

    public abstract void doAction(String[] args);

}





