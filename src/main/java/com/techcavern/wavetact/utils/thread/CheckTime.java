package com.techcavern.wavetact.utils.thread;

import com.techcavern.wavetact.utils.databaseUtils.BanTimeUtils;
import com.techcavern.wavetact.utils.databaseUtils.QuietTimeUtils;
import com.techcavern.wavetact.utils.objects.UTime;
import com.techcavern.wavetact.utils.*;
import org.pircbotx.PircBotX;

import java.util.concurrent.TimeUnit;

public class CheckTime implements Runnable {

    private boolean loaded = false;

    @Override
    public void run(){
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException c){
            // ignored
        }
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
                for (int i = 0; i < GeneralRegistry.BanTimes.size(); i++) {
                    UTime utimeObject = GeneralRegistry.BanTimes.get(i);
                    try {
                        if (System.currentTimeMillis() >= utimeObject.getTime()  + utimeObject.getInit()) {
                            PircBotX botObject = GetUtils.getBotByNetwork(utimeObject.getNetworkName());
                            IRCUtils.setMode(GetUtils.getChannelbyName(botObject, utimeObject.getChannelName()), botObject, "-b ", utimeObject.getSomething());
                            GeneralRegistry.BanTimes.remove(i);
                            BanTimeUtils.saveBanTimes();
                        }
                    } catch (IllegalArgumentException | NullPointerException e) {
                        // ignored
                    }
                }

                for (int i = 0; i < GeneralRegistry.QuietTimes.size(); i++) {
                    UTime utimeObject = GeneralRegistry.QuietTimes.get(i);
                    try {
                        if (System.currentTimeMillis() >= utimeObject.getTime() + utimeObject.getInit()) {
                            PircBotX botObject = GetUtils.getBotByNetwork(utimeObject.getNetworkName());
                            switch (utimeObject.getType().toLowerCase()) {
                                case "u":
                                    IRCUtils.setMode(GetUtils.getChannelbyName(botObject, utimeObject.getChannelName()), botObject, "-b ~q:", utimeObject.getSomething());
                                    break;
                                case "c":
                                    IRCUtils.setMode(GetUtils.getChannelbyName(botObject, utimeObject.getChannelName()), botObject, "-q ", utimeObject.getSomething());
                                    break;
                                case "i":
                                    IRCUtils.setMode(GetUtils.getChannelbyName(botObject, utimeObject.getChannelName()), botObject, "-b m:", utimeObject.getSomething());
                                    break;
                            }
                            GeneralRegistry.QuietTimes.remove(i);
                            QuietTimeUtils.saveQuietTimes();
                        }
                    } catch (IllegalArgumentException | NullPointerException e) {
                        // ignored
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

