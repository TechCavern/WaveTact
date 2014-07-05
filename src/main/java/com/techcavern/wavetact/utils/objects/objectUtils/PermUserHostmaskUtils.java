package com.techcavern.wavetact.utils.objects.objectUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.PermUser;
import com.techcavern.wavetact.utils.objects.PermUserHostmask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jztech101 on 7/4/14.
 */
public class PermUserHostmaskUtils {
    @SuppressWarnings("unchecked")
    public static void loadPermUserHostmasks() {
        JSONFile file = new JSONFile("PermUserHostmasks.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> actions = file.read(List.class);
                GeneralRegistry.PermUserHostmasks.clear();
                GeneralRegistry.PermUserHostmasks.addAll(actions.stream().map(act -> new PermUserHostmask((String) act.get("hostmask"),
                        (String) act.get("realname"),
                        ((Double) act.get("PermLevel")).intValue())).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }
    public static void savePermUserHostmasks() {
        JSONFile file = new JSONFile("PermUserHostmasks.json");
        try {
            file.write(GeneralRegistry.PermUserHostmasks);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }
}
