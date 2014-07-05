package com.techcavern.wavetact.utils.objects.objectUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.SimpleMessage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jztech101 on 7/5/14.
 */
public class ControllerUtils {
    public static void loadControllers() {
        JSONFile file = new JSONFile("Controllers.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> messages = file.read();
                GeneralRegistry.Controllers.clear();
                GeneralRegistry.Controllers.addAll(messages.stream().collect(Collectors.toList()));
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
