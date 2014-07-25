package com.techcavern.wavetact.utils.databaseUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.Global;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jztech101 on 7/5/14.
 */
public class GlobalUtils {
    public static void loadGlobals() {
        JSONFile file = new JSONFile("Globals.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> permChannels = file.read(List.class);

                GeneralRegistry.Globals.clear();
                GeneralRegistry.Globals.addAll(permChannels.stream().map(perms -> new Global((String) perms.get("Network"),
                        (String) perms.get("user"))).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveGlobals() {
        JSONFile file = new JSONFile("Globals.json");
        try {
            file.write(GeneralRegistry.Globals);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }
}
