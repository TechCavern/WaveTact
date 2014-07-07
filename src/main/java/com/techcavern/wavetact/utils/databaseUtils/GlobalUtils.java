package com.techcavern.wavetact.utils.databaseUtils;

import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;

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
                List<String> Globals = file.read();
                GeneralRegistry.Globals.clear();
                GeneralRegistry.Globals.addAll(Globals.stream().collect(Collectors.toList()));
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
