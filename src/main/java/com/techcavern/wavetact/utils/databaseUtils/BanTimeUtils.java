package com.techcavern.wavetact.utils.databaseUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.Constants;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.UTime;

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
                Constants.BanTimes.clear();
                Constants.BanTimes.addAll(bantimes.stream().map(bans -> new UTime((String) bans.get("hostmask"),
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
            file.write(Constants.BanTimes);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    public static UTime getBanTime(String hostmask, String networkname, String channelname) {
        for (UTime x : Constants.BanTimes) {
            if (x.getHostmask().equals(hostmask) && x.getNetworkName().equals(networkname) && x.getChannelName().equals(channelname)) {
                return x;
            }
        }
        return null;
    }
}
