package com.techcavern.wavetact.utils.databaseUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.UTime;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jztech101 on 7/4/14.
 */
public class QuietTimeUtils {
    @SuppressWarnings("unchecked")
    public static void loadQuietTimes() {
        JSONFile file = new JSONFile("QuietTimes.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> quiettimes = file.read(List.class);
                GeneralRegistry.QuietTimes.clear();
                GeneralRegistry.QuietTimes.addAll(quiettimes.stream().map(quiets -> new UTime((String) quiets.get("Something"),
                        (String) quiets.get("networkName"),
                        (String) quiets.get("type"),
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
            file.write(GeneralRegistry.QuietTimes);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    public static UTime getQuietTime(String hostmask) {
        for (UTime x : GeneralRegistry.QuietTimes) {
            if (x.getSomething().equals(hostmask)) {
                return x;
            }
        }
        return null;
    }
}
