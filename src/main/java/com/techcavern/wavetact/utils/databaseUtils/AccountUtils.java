package com.techcavern.wavetact.utils.databaseUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.Account;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AccountUtils {
    @SuppressWarnings("unchecked")
    public static void loadAccounts() {
        JSONFile file = new JSONFile("Accounts.json");
        if (file.exists()) {
            try {
                List<LinkedTreeMap> accounts = file.read(List.class);
                GeneralRegistry.Accounts.clear();
                GeneralRegistry.Accounts.addAll(accounts.stream().map(Acc -> new Account((String) Acc.get("AuthAccount"),
                        (String) Acc.get("AuthPassword"))).collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
                ErrorUtils.handleException(e);
            }
        }
    }

    public static void saveAccounts() {
        JSONFile file = new JSONFile("Accounts.json");
        try {
            file.write(GeneralRegistry.Accounts);
        } catch (IOException e) {
            ErrorUtils.handleException(e);
        }
    }

    public static Account getAccount(String Account) {
        for (Account account : GeneralRegistry.Accounts) {
            if (account.getAuthAccount().equals(Account)) {
                return account;
            }
        }
        return null;
    }
}
