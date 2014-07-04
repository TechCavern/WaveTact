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
public class QuietTimeUtils {
    @SuppressWarnings("unchecked")
    public static void loadQuietTimes() {
        JSONFile file = new JSONFile("QuietTimes.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> actions = file.read(List.class);
                GeneralRegistry.QuietTimes.clear();
                GeneralRegistry.QuietTimes.addAll(actions.stream().map(act -> new UTime((String) act.get("hostmask"),
                        (String) act.get("network"),
                        (String) act.get("type"),
                        (String) act.get("channel"),
                        ((Double) act.get("time")).longValue())).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveQuietTimes() {
        JSONFile file = new JSONFile("BanTimes.json");
        try {
            file.write(GeneralRegistry.QuietTimes);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }
}
