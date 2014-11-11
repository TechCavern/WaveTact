package com.techcavern.wavetact.utils.runnables;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.database.BanTimeUtils;
import com.techcavern.wavetact.utils.database.QuietTimeUtils;
import com.techcavern.wavetact.utils.objects.UTime;
import org.pircbotx.PircBotX;

import java.util.concurrent.TimeUnit;

public class BanTimer implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException c) {
            // ignored
        }
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
                for (int i = 0; i < GeneralRegistry.BanTimes.size(); i++) {
                    UTime utimeObject = GeneralRegistry.BanTimes.get(i);
                    try {
                        if (System.currentTimeMillis() >= utimeObject.getTime() + utimeObject.getInit()) {
                            PircBotX botObject = GetUtils.getBotByNetwork(utimeObject.getNetworkName());
                            IRCUtils.setMode(GetUtils.getChannelbyName(botObject, utimeObject.getChannelName()), botObject, "-b ", utimeObject.getHostmask());
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
                            IRCUtils.setMode(GetUtils.getChannelbyName(botObject, utimeObject.getChannelName()), botObject, "-" + GeneralRegistry.QuietBans.get(utimeObject.getType().toLowerCase()), utimeObject.getHostmask());
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

