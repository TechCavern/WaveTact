package com.techcavern.wavetact.thread;

import com.techcavern.wavetact.objects.UTime;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

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
                        IRCUtils.loadBanTimes();
                        IRCUtils.loadQuietTimes();
                        loaded = true;
                    }
                } catch (NullPointerException ignored) {}

                if (!loaded)
                    continue;

                for (int i = 0; i < GeneralRegistry.BanTimes.size(); i++) {
                    UTime x = GeneralRegistry.BanTimes.get(i);
                    try {
                        if (System.currentTimeMillis() >= x.getTime()) {
                            PircBotX b = IRCUtils.getBotByNetwork(x.getNetwork());
                            IRCUtils.setMode(IRCUtils.getChannelbyName(b, x.getChannel()), b, "-b ", x.getHostmask());
                            GeneralRegistry.BanTimes.remove(x);
                            IRCUtils.saveBanTimes();
                        }
                    } catch (IllegalArgumentException | NullPointerException e) {
                        // ignored
                    }
                }

                for (int i = 0; i < GeneralRegistry.QuietTimes.size(); i++) {
                    UTime x = GeneralRegistry.QuietTimes.get(i);
                    try {
                        if (System.currentTimeMillis() >= x.getTime()) {
                            PircBotX b = IRCUtils.getBotByNetwork(x.getNetwork());
                            switch (x.getType().toLowerCase()) {
                                case "u":
                                    IRCUtils.setMode(IRCUtils.getChannelbyName(b, x.getChannel()), b, "-b ~q:", x.getHostmask());
                                    break;
                                case "c":
                                    IRCUtils.setMode(IRCUtils.getChannelbyName(b, x.getChannel()), b, "-q ", x.getHostmask());
                                    break;
                                case "i":
                                    IRCUtils.setMode(IRCUtils.getChannelbyName(b, x.getChannel()), b, "-b m:", x.getHostmask());
                                    break;
                            }
                            GeneralRegistry.QuietTimes.remove(x);
                            IRCUtils.saveQuietTimes();
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

