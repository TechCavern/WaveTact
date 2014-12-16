package com.techcavern.wavetact.utils.databaseUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.Constants;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.TimedObj;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class QuietTimeUtils {
    @SuppressWarnings("unchecked")
    public static void loadQuietTimes() {
        JSONFile file = new JSONFile("QuietTimes.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> quiettimes = file.read(List.class);
                Constants.QuietTimes.clear();
                Constants.QuietTimes.addAll(quiettimes.stream().map(quiets -> new TimedObj((String) quiets.get("hostmask"),
                        (String) quiets.get("networkName"),
                        (String) quiets.get("property"),
                        (String) quiets.get("channelName"),
                        ((Double) quiets.get("time")).longValue(),
                        ((Double) quiets.get("init")).longValue())).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveQuietTimes() {
        JSONFile file = new JSONFile("QuietTimes.json");
        try {
            file.write(Constants.QuietTimes);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    public static TimedObj getQuietTime(String hostmask, String networkname, String channelname) {
        for (TimedObj x : Constants.QuietTimes) {
            if (x.getHostmask().equals(hostmask) && x.getNetworkName().equals(networkname) && x.getChannelName().equals(channelname)) {
                return x;
            }
        }
        return null;
    }
}
