package com.techcavern.wavetact.utils.olddatabaseUtils;

import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;

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
                Registry.Controllers.clear();
                Registry.Controllers.addAll(controllers.stream().collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveControllers() {
        JSONFile file = new JSONFile("Controllers.json");
        try {
            file.write(Registry.Controllers);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }
}
