package com.techcavern.wavetact.thread;

import com.techcavern.wavetact.objects.UTime;
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
                        LoadUtils.loadBanTimes();
                        LoadUtils.loadQuietTimes();
                        loaded = true;
                    }
                } catch (NullPointerException ignored) {}

                if (!loaded)
                    continue;

                for (int i = 0; i < GeneralRegistry.BanTimes.size(); i++) {
                    UTime x = GeneralRegistry.BanTimes.get(i);
                    try {
                        if (System.currentTimeMillis() >= x.getTime()) {
                            PircBotX b = GetUtils.getBotByNetwork(x.getNetwork());
                            IRCUtils.setMode(GetUtils.getChannelbyName(b, x.getChannel()), b, "-b ", x.getHostmask());
                            GeneralRegistry.BanTimes.remove(x);
                            SaveUtils.saveBanTimes();
                        }
                    } catch (IllegalArgumentException | NullPointerException e) {
                        // ignored
                    }
                }

                for (int i = 0; i < GeneralRegistry.QuietTimes.size(); i++) {
                    UTime x = GeneralRegistry.QuietTimes.get(i);
                    try {
                        if (System.currentTimeMillis() >= x.getTime()) {
                            PircBotX b = GetUtils.getBotByNetwork(x.getNetwork());
                            switch (x.getType().toLowerCase()) {
                                case "u":
                                    IRCUtils.setMode(GetUtils.getChannelbyName(b, x.getChannel()), b, "-b ~q:", x.getHostmask());
                                    break;
                                case "c":
                                    IRCUtils.setMode(GetUtils.getChannelbyName(b, x.getChannel()), b, "-q ", x.getHostmask());
                                    break;
                                case "i":
                                    IRCUtils.setMode(GetUtils.getChannelbyName(b, x.getChannel()), b, "-b m:", x.getHostmask());
                                    break;
                            }
                            GeneralRegistry.QuietTimes.remove(x);
                            SaveUtils.saveQuietTimes();
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

