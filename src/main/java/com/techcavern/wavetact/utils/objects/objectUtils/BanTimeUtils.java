package com.techcavern.wavetact.utils.objects.objectUtils;

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
public class BanTimeUtils {
    @SuppressWarnings("unchecked")
    public static void loadBanTimes() {
        JSONFile file = new JSONFile("BanTimes.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> actions = file.read(List.class);
                GeneralRegistry.BanTimes.clear();
                GeneralRegistry.BanTimes.addAll(actions.stream().map(act -> new UTime((String) act.get("hostmask"),
                        (String) act.get("network"),
                        (String) act.get("type"),
                        (String) act.get("channel"),
                        ((Double) act.get("time")).longValue())).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveBanTimes() {
        JSONFile file = new JSONFile("BanTimes.json");
        try {
            file.write(GeneralRegistry.BanTimes);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    public static UTime getBanTime(String hostmask) {
        for (UTime x : GeneralRegistry.BanTimes) {
            if (x.getHostmask().equals(hostmask)) {
                return x;
            }
        }
        return null;
    }
}
