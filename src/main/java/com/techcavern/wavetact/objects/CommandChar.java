package com.techcavern.wavetact.objects;

import com.techcavern.wavetact.utils.GeneralRegistry;
import org.pircbotx.PircBotX;

/**
 * Created by jztech101 on 7/3/14.
 */
public class CommandChar {
    private final String chard;
    private final PircBotX bot;

    public CommandChar(String c, PircBotX d){
        this.chard = c;
        this.bot = d;
        create();
    }

    void create(){
        GeneralRegistry.CommandChars.add(this);
    }
    public String getCommandChar(){
        return this.chard;
    }

    public PircBotX getBot(){
        return this.bot;
    }



}
