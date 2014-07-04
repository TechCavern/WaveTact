package com.techcavern.wavetact.utils.thread;

import com.techcavern.wavetact.utils.objects.objectUtils.BanTimeUtils;
import com.techcavern.wavetact.utils.objects.objectUtils.QuietTimeUtils;
import com.techcavern.wavetact.utils.objects.UTime;
import com.techcavern.wavetact.utils.*;
import org.pircbotx.PircBotX;

import java.util.concurrent.TimeUnit;

public class CheckTime implements Runnable {

    private boolean loaded = false;

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
                try {
                    if (!loaded) {
                        BanTimeUtils.loadBanTimes();
                        QuietTimeUtils.loadQuietTimes();
                        loaded = true;
                    }
                } catch (NullPointerException ignored) {
                }

                if (!loaded)
                    continue;

                for (int i = 0; i < GeneralRegistry.BanTimes.size(); i++) {
                    UTime utimeObject = GeneralRegistry.BanTimes.get(i);
                    try {
                        if (System.currentTimeMillis() >= utimeObject.getTime()) {
                            PircBotX botObject = GetUtils.getBotByNetwork(utimeObject.getNetworkName());
                            IRCUtils.setMode(GetUtils.getChannelbyName(botObject, utimeObject.getChannelName()), botObject, "-b ", utimeObject.getHostmask());
                            GeneralRegistry.BanTimes.remove(utimeObject);
                            BanTimeUtils.saveBanTimes();
                        }
                    } catch (IllegalArgumentException | NullPointerException e) {
                        // ignored
                    }
                }

                for (int i = 0; i < GeneralRegistry.QuietTimes.size(); i++) {
                    UTime utimeObject = GeneralRegistry.QuietTimes.get(i);
                    try {
                        if (System.currentTimeMillis() >= utimeObject.getTime()) {
                            PircBotX botObject = GetUtils.getBotByNetwork(utimeObject.getNetworkName());
                            switch (utimeObject.getType().toLowerCase()) {
                                case "u":
                                    IRCUtils.setMode(GetUtils.getChannelbyName(botObject, utimeObject.getChannelName()), botObject, "-b ~q:", utimeObject.getHostmask());
                                    break;
                                case "c":
                                    IRCUtils.setMode(GetUtils.getChannelbyName(botObject, utimeObject.getChannelName()), botObject, "-q ", utimeObject.getHostmask());
                                    break;
                                case "i":
                                    IRCUtils.setMode(GetUtils.getChannelbyName(botObject, utimeObject.getChannelName()), botObject, "-b m:", utimeObject.getHostmask());
                                    break;
                            }
                            GeneralRegistry.QuietTimes.remove(utimeObject);
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

