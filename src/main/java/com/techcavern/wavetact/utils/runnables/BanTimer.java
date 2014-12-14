package com.techcavern.wavetact.utils.runnables;

import com.techcavern.wavetact.utils.Constants;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.databaseUtils.BanTimeUtils;
import com.techcavern.wavetact.utils.databaseUtils.QuietTimeUtils;
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
                for (int i = 0; i < Constants.BanTimes.size(); i++) {
                    UTime utimeObject = Constants.BanTimes.get(i);
                    try {
                        if (System.currentTimeMillis() >= utimeObject.getTime() + utimeObject.getInit()) {
                            PircBotX networkObject = GetUtils.getBotByNetwork(utimeObject.getNetworkName());
                            IRCUtils.setMode(GetUtils.getChannelbyName(networkObject, utimeObject.getChannelName()), networkObject, "-b ", utimeObject.getHostmask());
                            Constants.BanTimes.remove(i);
                            BanTimeUtils.saveBanTimes();
                        }
                    } catch (IllegalArgumentException | NullPointerException e) {
                        // ignored
                    }
                }

                for (int i = 0; i < Constants.QuietTimes.size(); i++) {
                    UTime utimeObject = Constants.QuietTimes.get(i);
                    try {
                        if (System.currentTimeMillis() >= utimeObject.getTime() + utimeObject.getInit()) {
                            PircBotX networkObject = GetUtils.getBotByNetwork(utimeObject.getNetworkName());
                            IRCUtils.setMode(GetUtils.getChannelbyName(networkObject, utimeObject.getChannelName()), networkObject, "-" + Constants.QuietBans.get(utimeObject.getType().toLowerCase()), utimeObject.getHostmask());
                            Constants.QuietTimes.remove(i);
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

