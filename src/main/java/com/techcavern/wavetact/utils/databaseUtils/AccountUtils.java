package com.techcavern.wavetact.utils.databaseUtils;

import com.techcavern.wavetact.utils.Registry;
import static wavetactdb.Tables.*;

public class AccountUtils {
    @SuppressWarnings("unchecked")
    public static void getA() {
    }

    public static void saveAccounts() {

    }

    public static String getAccountPassword(String Account) {
        return Registry.WaveTactDB.select().from(ACCOUNTS).where(ACCOUNTS.username.eq(Account)).fetch().getValue(ACCOUNTS.password);
    }
}
