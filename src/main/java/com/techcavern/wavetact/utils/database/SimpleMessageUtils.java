package com.techcavern.wavetact.utils.database;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.file.JSONFile;
import com.techcavern.wavetact.utils.objects.SimpleMessage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SimpleMessageUtils {
    @SuppressWarnings("unchecked")
    public static void loadSimpleMessages() {
        JSONFile file = new JSONFile("SimpleMessages.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> messages = file.read();
                GeneralRegistry.SimpleMessages.clear();

                GeneralRegistry.SimpleMessages.addAll(messages.stream().map(msg -> new SimpleMessage(
                        ((ArrayList<String>) msg.get("comid")).get(0),
                        ((Double) msg.get("PermLevel")).intValue(),
                        (String) msg.get("message"),
                        (Boolean) msg.get("locked"))).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveSimpleMessages() {
        JSONFile file = new JSONFile("SimpleMessages.json");
        try {
            file.write(GeneralRegistry.SimpleMessages);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    public static SimpleMessage getSimpleMessage(String SimpleAction) {
        for (SimpleMessage g : GeneralRegistry.SimpleMessages) {
            if (g.getCommand().equalsIgnoreCase(SimpleAction)) {
                return g;
            }
        }
        return null;

    }
}
