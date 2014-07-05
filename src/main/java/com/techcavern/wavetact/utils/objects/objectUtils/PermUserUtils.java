package com.techcavern.wavetact.utils.objects.objectUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.PermChannel;
import com.techcavern.wavetact.utils.objects.PermUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jztech101 on 7/4/14.
 */
public class PermUserUtils {
    @SuppressWarnings("unchecked")
    public static void loadPermUsers() {
        JSONFile file = new JSONFile("PermUsers.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> actions = file.read(List.class);
                GeneralRegistry.PermUsers.clear();
                GeneralRegistry.PermUsers.addAll(actions.stream().map(act -> new PermUser((String) act.get("PermNetwork"),
                        (List<PermChannel>) act.get("Channel"),
                        (String) act.get("PermUser"),
                        (Boolean) act.get("Global")).collect(Collectors.toList())));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void savePermUsers() {
        JSONFile file = new JSONFile("PermUsers.json");
        try {
            file.write(GeneralRegistry.PermUsers);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    public static PermUser getPermUserbyNick(String nick, String Network){
        for(PermUser c: GeneralRegistry.PermUsers){
            if(c.getPermUser().equals(nick) && c.getPermNetwork().equals(Network)){
                return c;
            }
        }
        return null;
    }

    public static PermChannel getPermLevelChannel(String Network, String nick, String Channel) {
        PermUser user = getPermUserbyNick(nick, Network);
        if (user != null) {
            for (PermChannel c : user.getPermChannel()) {
                if (c.getChannel().equals(Channel)) {
                    return c;
                }
            }
            return null;
        } else {
            return null;
        }
    }
}
