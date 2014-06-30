package com.techcavern.wavetact.thread;

import com.techcavern.wavetact.objects.UTime;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.concurrent.TimeUnit;

public class CheckTime implements Runnable{


    @Override
    public void run() {
        while(true){
            try {
                TimeUnit.SECONDS.sleep(5);
                for(UTime x:GeneralRegistry.BanTimes) {
                    if(System.currentTimeMillis() >= x.getTime()){
                        PircBotX b = IRCUtils.getBotByNetwork(x.getNetwork()){
                                   IRCUtils.setMode(IRCUtils.getChannelbyName(b,x.getChannel()), b, "+b", x.getHostmask());

                            }

                }}

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }

    }
}