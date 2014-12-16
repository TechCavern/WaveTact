package com.techcavern.wavetact.utils.databaseUtils;

import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class IRCBLUtils {
    public static void loadIRCBLs() {
        JSONFile file = new JSONFile("IRCBLs.json");
        if (file.exists()) {
            try {
                List<String> controllers = file.read();
                Registry.IRCBLs.clear();
                Registry.IRCBLs.addAll(controllers.stream().collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveIRCBLs() {
        JSONFile file = new JSONFile("IRCBLs.json");
        try {
            file.write(Registry.IRCBLs);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }
}
