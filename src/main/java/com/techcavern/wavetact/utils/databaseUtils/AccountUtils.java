package com.techcavern.wavetact.utils.databaseUtils;

import com.techcavern.wavetact.utils.Registry;
import org.jooq.Record;
import org.jooq.Result;

import static com.techcavern.wavetactdb.Tables.*;

public class AccountUtils {
    @SuppressWarnings("unchecked")


    public static String getAccountPassword(String Account) {
        Result<Record> accountrecord =  Registry.WaveTactDB.select().from(ACCOUNTS).where(ACCOUNTS.USERNAME.eq(Account)).fetch();
        if(accountrecord.size() > 0){
            return accountrecord.get(0).getValue(ACCOUNTS.PASSWORD);
        }else{
            return null;
        }
    }
    public static String setAccountPassword(String Account, String password) {
        Result<Record> accountrecord =  Registry.WaveTactDB.select().from(ACCOUNTS).where(ACCOUNTS.USERNAME.eq(Account)).fetch();
        if(accountrecord.size() > 0){
            return accountrecord.get(0).setValue(ACCOUNTS.PASSWORD, password);
        }else{
            return null;
        }
    }
}
