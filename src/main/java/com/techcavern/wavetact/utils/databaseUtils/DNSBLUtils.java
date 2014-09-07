package com.techcavern.wavetact.utils.databaseUtils;

import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class DNSBLUtils {
    public static void loadDNSBLs() {
        JSONFile file = new JSONFile("DNSBLs.json");
        if (file.exists()) {
            try {
                List<String> controllers = file.read();
                GeneralRegistry.DNSBLs.clear();
                GeneralRegistry.DNSBLs.addAll(controllers.stream().collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveDNSBLs() {
        JSONFile file = new JSONFile("DNSBLs.json");
        try {
            file.write(GeneralRegistry.DNSBLs);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }
}
