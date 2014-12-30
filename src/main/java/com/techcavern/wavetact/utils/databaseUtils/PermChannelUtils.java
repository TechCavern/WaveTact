package com.techcavern.wavetact.utils.databaseUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.objects.PermChannel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class PermChannelUtils {
    @SuppressWarnings("unchecked")
    public static void loadPermChannels() {
        JSONFile file = new JSONFile("PermChannels.json");
        if (file.exists()) {
            try {

                List<LinkedTreeMap> permChannels = file.read(List.class);

                Registry.PermChannels.clear();
                Registry.PermChannels.addAll(permChannels.stream().map(perms -> new PermChannel((String) perms.get("channelName"),
                        ((Double) perms.get("PermLevel")).intValue(),
                        (String) perms.get("networkName"),
                        (String) perms.get("property"))).collect(Collectors.toList()));


            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void savePermChannels() {
        JSONFile file = new JSONFile("PermChannels.json");
        try {
            file.write(Registry.PermChannels);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    public static PermChannel getPermLevelChannel(String Network, String nick, String Channel) {
        if (nick != null) {
            for (PermChannel c : Registry.PermChannels) {
                if (c.getChannelName().equals(Channel) && c.getPermUser().equalsIgnoreCase(nick) && c.getNetworkName().equals(Network)) {
                    return c;
                }
            }
            return null;
        } else {
            return null;
        }
    }
}


