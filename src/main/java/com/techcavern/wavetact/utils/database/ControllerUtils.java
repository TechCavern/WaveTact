package com.techcavern.wavetact.utils.database;

import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.file.JSONFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class ControllerUtils {
    public static void loadControllers() {
        JSONFile file = new JSONFile("Controllers.json");
        if (file.exists()) {
            try {
                List<String> controllers = file.read();
                GeneralRegistry.Controllers.clear();
                GeneralRegistry.Controllers.addAll(controllers.stream().collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveControllers() {
        JSONFile file = new JSONFile("Controllers.json");
        try {
            file.write(GeneralRegistry.Controllers);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }
}
