package com.techcavern.wavetact.utils.databaseUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.fileUtils.JSONFile;
import com.techcavern.wavetact.utils.objects.Account;
import org.jooq.Record;
import test.database.tables.Accounts;
import test.database.tables.records.AccountsRecord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
