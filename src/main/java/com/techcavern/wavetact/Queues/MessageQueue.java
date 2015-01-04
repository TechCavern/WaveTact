package com.techcavern.wavetact.Queues;

import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.jooq.Record;
import org.pircbotx.PircBotX;

import java.util.concurrent.TimeUnit;

import static com.techcavern.wavetactdb.Tables.BANS;

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
                    Registry.MessageQueue.remove(0);
                    TimeUnit.MILLISECONDS.sleep(1500);
                }
            }catch(Exception e){
            }
        }
    }

}

