package com.techcavern.wavetact.utils.databaseUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.Constants;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
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
                Constants.SimpleMessages.clear();

                Constants.SimpleMessages.addAll(messages.stream().map(msg -> new SimpleMessage(
                        ((ArrayList<String>) msg.get("comID")).get(0),
                        ((Double) msg.get("permLevel")).intValue(),
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
            file.write(Constants.SimpleMessages);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    public static SimpleMessage getSimpleMessage(String SimpleAction) {
        for (SimpleMessage g : Constants.SimpleMessages) {
            if (g.getCommand().equalsIgnoreCase(SimpleAction)) {
                return g;
            }
        }
        return null;

    }
}
