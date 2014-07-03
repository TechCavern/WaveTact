package com.techcavern.wavetact.objects;

import com.techcavern.wavetact.utils.GeneralRegistry;

public abstract class CommandLine {

    private final String[] argument;
    private final String helpstring;

    protected CommandLine(String[] sa, String hs){
        this.argument = sa;
        this.helpstring = hs;
        create();

    }

    public void create(){
        GeneralRegistry.CommandLines.add(this);
    }

    public String[] getArgument(){
        return this.argument;
    }
    public String getHelpString(){
        return this.helpstring;
    }
    public abstract void doAction();

}





