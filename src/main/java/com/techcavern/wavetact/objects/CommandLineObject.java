package com.techcavern.wavetact.objects;

public interface CommandLineObject
{

    public String getShortArgument();
    public String getLongArgument();
    public String getHelpString();
    public void doAction();

}
