package com.techcavern.wavetact.utils.databaseUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.ChannelProperty;
import com.techcavern.wavetact.utils.objects.ChannelUserProperty;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class RelayUtils {
    public static void loadRelayBots() {
        JSONFile file = new JSONFile("RelayBots.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> relayBots = file.read(List.class);

                Registry.RelayBots.clear();
                Registry.RelayBots.addAll(relayBots.stream().map(relays -> new ChannelUserProperty(
                        (String) relays.get("networkName"),
                        (String) relays.get("channelName"),
                        (String) relays.get("user"),
                        (String) relays.get("property"))).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveRelayBots() {
        JSONFile file = new JSONFile("RelayBots.json");
        try {
            file.write(Registry.RelayBots);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }
}
