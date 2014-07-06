package com.techcavern.wavetact.utils.objects.objectUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.PermChannel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jztech101 on 7/4/14.
 */
public class PermChannelUtils {
    @SuppressWarnings("unchecked")
    public static void loadPermChannels() {
        JSONFile file = new JSONFile("PermChannels.json");
        if (file.exists()) {
            try {

                List<LinkedTreeMap> permChannels = file.read(List.class);

                GeneralRegistry.PermChannels.clear();
                GeneralRegistry.PermChannels.addAll(permChannels.stream().map(perms -> new PermChannel((String) perms.get("Channel"),
                        ((Double) perms.get("PermLevel")).intValue(),
                        (Boolean) perms.get("auto"),
                        (String) perms.get("PermNetwork"),
                        (String) perms.get("permuser"))).collect(Collectors.toList()));


            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void savePermChannels() {
        JSONFile file = new JSONFile("PermChannels.json");
        try {
            file.write(GeneralRegistry.PermChannels);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    public static PermChannel getPermLevelChannel(String Network, String nick, String Channel) {
        for (PermChannel c : GeneralRegistry.PermChannels) {
            if (c.getChannel().equals(Channel) && c.getPermUser().equals(nick) && c.getPermNetwork().equals(Network)) {
                return c;
            }
        }
        return null;
    }
}


