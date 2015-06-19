package com.techcavern.wavetact.queues;

import com.techcavern.wavetact.utils.Registry;

import java.util.concurrent.TimeUnit;

public class MessageQueue implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException c) {
        }
        while (true) {
            try {
                if (Registry.MessageQueue.size() > 0) {
                    Registry.MessageQueue.get(0).getNetwork().sendRaw().rawLine(Registry.MessageQueue.get(0).getProperty());
             //       System.out.println(Registry.MessageQueue.get(0).getProperty());
                    Registry.MessageQueue.remove(0);
                }
                TimeUnit.MILLISECONDS.sleep(1000);
            }catch(Exception e){
            }
        }
    }

}

