package com.techcavern.wavetact.utils.olddatabaseUtils;

import com.techcavern.wavetact.utils.Registry;
import org.jooq.Record;
import org.jooq.Result;

import static com.techcavern.wavetactdb.Tables.*;

public class AccountUtils {
    @SuppressWarnings("unchecked")

    public static void createAccount(String account, String password){
        Registry.WaveTactDB.insertInto(ACCOUNTS).values(account, password).execute();
    }

    public static String getAccountPassword(String account) {
        Result<Record> accountrecord =  Registry.WaveTactDB.select().from(ACCOUNTS).where(ACCOUNTS.USERNAME.eq(account)).fetch();
        if(accountrecord.size() > 0){
            return accountrecord.get(0).getValue(ACCOUNTS.PASSWORD);
        }else{
            return null;
        }
    }
    public static void deleteAccount(String account) {
        Registry.WaveTactDB.delete(ACCOUNTS).where(ACCOUNTS.USERNAME.eq(account));
    }
    public static void setAccountPassword(String account, String password) {
        Result<Record> accountrecord =  Registry.WaveTactDB.select().from(ACCOUNTS).where(ACCOUNTS.USERNAME.eq(account)).fetch();
        if(accountrecord.size() > 0){
            accountrecord.get(0).setValue(ACCOUNTS.PASSWORD, password);
        }
    }
}
