package com.techcavern.wavetact.utils.databaseUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.TimedBan;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class BanTimeUtils {
    @SuppressWarnings("unchecked")
    public static void loadBanTimes() {
        JSONFile file = new JSONFile("BanTimes.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> bantimes = file.read(List.class);
                Registry.BanTimes.clear();
                Registry.BanTimes.addAll(bantimes.stream().map(bans -> new TimedBan((String) bans.get("hostmask"),
                        (String) bans.get("networkName"),
                        (String) bans.get("property"),
                        (String) bans.get("channelName"),
                        ((Double) bans.get("time")).longValue(),
                        ((Double) bans.get("init")).longValue())).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveBanTimes() {
        JSONFile file = new JSONFile("BanTimes.json");
        try {
            file.write(Registry.BanTimes);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    public static TimedBan getBanTime(String hostmask, String networkname, String channelname) {
        for (TimedBan x : Registry.BanTimes) {
            if (x.getHostmask().equals(hostmask) && x.getNetworkName().equals(networkname) && x.getChannelName().equals(channelname)) {
                return x;
            }
        }
        return null;
    }
}
