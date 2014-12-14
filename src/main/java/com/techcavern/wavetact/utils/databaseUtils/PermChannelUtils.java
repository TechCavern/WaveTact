package com.techcavern.wavetact.utils.databaseUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.Constants;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.PermChannel;

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

                Constants.PermChannels.clear();
                Constants.PermChannels.addAll(permChannels.stream().map(perms -> new PermChannel((String) perms.get("Channel"),
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
            file.write(Constants.PermChannels);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    public static PermChannel getPermLevelChannel(String Network, String nick, String Channel) {
        if (nick != null) {
            for (PermChannel c : Constants.PermChannels) {
                if (c.getChannel().equals(Channel) && c.getPermUser().equalsIgnoreCase(nick) && c.getPermNetwork().equals(Network)) {
                    return c;
                }
            }
            return null;
        } else {
            return null;
        }
    }
}


