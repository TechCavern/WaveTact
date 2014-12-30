package com.techcavern.wavetact.utils.databaseUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.objects.NetworkAdmin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class NetAdminUtils {
    public static void loadNetworkAdmins() {
        JSONFile file = new JSONFile("NetworkAdmins.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> permChannels = file.read(List.class);

                Registry.NetworkAdmins.clear();
                Registry.NetworkAdmins.addAll(permChannels.stream().map(perms -> new NetworkAdmin((String) perms.get("Network"),
                        (String) perms.get("user"))).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveNetworkAdmins() {
        JSONFile file = new JSONFile("NetworkAdmins.json");
        try {
            file.write(Registry.NetworkAdmins);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }
}
