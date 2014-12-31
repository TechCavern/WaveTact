package com.techcavern.wavetact.utils;

import org.jooq.Record;
import org.jooq.Result;

import static com.techcavern.wavetactdb.Tables.ACCOUNTS;
import static com.techcavern.wavetactdb.Tables.BANS;

/**
 * Created by jztech101 on 12/30/14.
 */
public class databaseUtils {
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
    public static void setAccountPassword(String account, String password) {
        Result<Record> accountrecord =  Registry.WaveTactDB.select().from(ACCOUNTS).where(ACCOUNTS.USERNAME.eq(account)).fetch();
        if(accountrecord.size() > 0){
            accountrecord.get(0).setValue(ACCOUNTS.PASSWORD, password);
        }
    }
    public static Result<Record> getBans(){
        return Registry.WaveTactDB.select().from(BANS).fetch();
    }
}
