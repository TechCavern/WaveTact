package com.techcavern.wavetact.runnables;

import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.DatabaseUtils;
import static com.techcavern.wavetactdb.Tables.*;

import org.jooq.Record;
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
                boolean hasNext = false;
                for (Record banRecord: DatabaseUtils.getBans()) {
                    try {
                        if (System.currentTimeMillis() >= ((Long)banRecord.getValue(BANS.TIME) + (Long)banRecord.getValue(BANS.INIT))) {
                            PircBotX networkObject = IRCUtils.getBotByNetworkName(banRecord.getValue(BANS.NETWORK));
                            if(banRecord.getValue(BANS.ISMUTE))
                                IRCUtils.setMode(IRCUtils.getChannelbyName(networkObject, banRecord.getValue(BANS.CHANNEL)), networkObject, "-" + Registry.QuietBans.get(banRecord.getValue(BANS.PROPERTY)), banRecord.getValue(BANS.HOSTMASK));
                            else
                                IRCUtils.setMode(IRCUtils.getChannelbyName(networkObject, banRecord.getValue(BANS.CHANNEL)), networkObject, "-b ", banRecord.getValue(BANS.HOSTMASK));
                            DatabaseUtils.deleteBan(banRecord.getValue(BANS.NETWORK), banRecord.getValue(BANS.CHANNEL), banRecord.getValue(BANS.HOSTMASK), banRecord.getValue(BANS.ISMUTE));
                        }else{
                            break;
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

