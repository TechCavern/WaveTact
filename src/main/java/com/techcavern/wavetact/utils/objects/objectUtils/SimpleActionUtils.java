package com.techcavern.wavetact.utils.objects.objectUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.SimpleAction;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jztech101 on 7/4/14.
 */
public class SimpleActionUtils {
    @SuppressWarnings("unchecked")
    public static void loadSimpleActions() {
        JSONFile file = new JSONFile("SimpleActions.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> actions = file.read(List.class);
                GeneralRegistry.SimpleActions.clear();
                GeneralRegistry.SimpleActions.addAll(actions.stream().map(act -> new SimpleAction(
                        ((ArrayList<String>) act.get("comid")).get(0),
                        ((Double) act.get("PermLevel")).intValue(),
                        (String) act.get("action"),
                        (Boolean) act.get("locked"))).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveSimpleActions() {
        JSONFile file = new JSONFile("SimpleActions.json");
        try {
            file.write(GeneralRegistry.SimpleActions);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }
}
