package com.techcavern.wavetact.utils;

import org.jooq.Record;
import org.jooq.Result;

import static com.techcavern.wavetactdb.Tables.*;

/**
 * Created by jztech101 on 12/30/14.
 */
public class DatabaseUtils {
    public static void addAccount(String account, String password) {
        Registry.WaveTactDB.insertInto(ACCOUNTS).values(account, password).execute();
    }

    public static Record getAccount(String account) {
        Result<Record> accountRecord = Registry.WaveTactDB.select().from(ACCOUNTS).where(ACCOUNTS.USERNAME.eq(account)).fetch();
        return getRecord(accountRecord);
    }
    public static void updateAccount(Record account){
        Registry.WaveTactDB.update(ACCOUNTS).set(account).where(ACCOUNTS.USERNAME.eq(account.getValue(ACCOUNTS.USERNAME)));
    }

    public static void removeAccount(String account) {
        Registry.WaveTactDB.delete(ACCOUNTS).where(ACCOUNTS.USERNAME.eq(account)).execute();
    }

    public static void addBan(String network, String channel, String hostmask, long init, long time, boolean isMute, String property) {
        Registry.WaveTactDB.insertInto(BANS).values(hostmask, network, channel, init, time, isMute, property).execute();
    }

    public static void removeBan(String network, String channel, String hostmask, boolean isMute) {
        Registry.WaveTactDB.delete(BANS).where(BANS.HOSTMASK.eq(hostmask).and(BANS.NETWORK.eq(network)).and(BANS.ISMUTE.eq(isMute).and(BANS.CHANNEL.eq(channel)))).execute();
    }

    public static Record getBan(String network, String channel, String hostmask, boolean isMute) {
        Result<Record> banRecord = Registry.WaveTactDB.select().from(BANS).where(BANS.HOSTMASK.eq(hostmask)).and(BANS.NETWORK.eq(network)).and(BANS.ISMUTE.eq(isMute).and(BANS.CHANNEL.eq(channel))).fetch();
        return getRecord(banRecord);
    }

    public static void updateBan(Record ban){
        Registry.WaveTactDB.update(BANS).set(ban).where(BANS.HOSTMASK.eq(ban.getValue(BANS.HOSTMASK))).and(BANS.NETWORK.eq(ban.getValue(BANS.NETWORK))).and(BANS.ISMUTE.eq(ban.getValue(BANS.ISMUTE)).and(BANS.CHANNEL.eq(ban.getValue(BANS.CHANNEL)))).execute();
    }

    public static Result<Record> getBans() {
        return Registry.WaveTactDB.select().from(BANS).orderBy(BANS.HOSTMASK.asc()).fetch();
    }

    public static String getConfig(String config) {
        Record configRecord = getRecord(Registry.WaveTactDB.select().from(CONFIG).where(CONFIG.PROPERTY.eq(config)).fetch());
        if (configRecord == null)
            return null;
        return configRecord.getValue(CONFIG.VALUE);
    }

    public static void removeConfig(String config) {
        Registry.WaveTactDB.delete(CONFIG).where(CONFIG.PROPERTY.eq(config)).execute();
    }

    public static void addConfig(String config, String value) {
        Registry.WaveTactDB.insertInto(CONFIG).values(config, value).execute();
    }

    public static Record getRecord(Result<Record> record) {
        if (record.size() > 0) {
            return record.get(0);
        } else {
            return null;
        }

    }

    public static Record getCustomCommand(String network, String channel, String command) {
        Result<Record> commandRecord = Registry.WaveTactDB.select().from(CUSTOMCOMMANDS).where(CUSTOMCOMMANDS.COMMAND.eq(command)).and(CUSTOMCOMMANDS.NETWORK.eq(network)).and(CUSTOMCOMMANDS.CHANNEL.eq(channel)).fetch();
        if (getRecord(commandRecord) == null) {
            commandRecord = Registry.WaveTactDB.select().from(CUSTOMCOMMANDS).where(CUSTOMCOMMANDS.COMMAND.eq(command)).and(CUSTOMCOMMANDS.NETWORK.isNull()).and(CUSTOMCOMMANDS.CHANNEL.isNull()).fetch();
        }
        return getRecord(commandRecord);

    }
    public static void updateCustomCommand(Record command){
        Registry.WaveTactDB.update(CUSTOMCOMMANDS).set(command).where(CUSTOMCOMMANDS.COMMAND.eq(command.getValue(CUSTOMCOMMANDS.COMMAND))).and(CUSTOMCOMMANDS.NETWORK.eq(command.getValue(CUSTOMCOMMANDS.NETWORK)).and(CUSTOMCOMMANDS.CHANNEL.eq(command.getValue(CUSTOMCOMMANDS.CHANNEL)))).execute();

    }
    public static Result<Record> getCustomCommands(String network, String channel) {
        return Registry.WaveTactDB.select().from(CUSTOMCOMMANDS).where(CUSTOMCOMMANDS.NETWORK.eq(network)).and(CUSTOMCOMMANDS.CHANNEL.eq(channel)).fetch();
    }

    public static void addCustomCommand(String network, String channel, String command, int permlevel, String value, boolean isLocked, boolean isAction) {
        Registry.WaveTactDB.insertInto(CUSTOMCOMMANDS).values(permlevel, channel, command, value, network, isLocked, isAction).execute();
    }

    public static void removeCustomCommand(String network, String channel, String command) {
        Registry.WaveTactDB.delete(CUSTOMCOMMANDS).where(CUSTOMCOMMANDS.COMMAND.eq(command)).and(CUSTOMCOMMANDS.NETWORK.eq(network)).and(CUSTOMCOMMANDS.CHANNEL.eq(channel)).execute();
    }

    public static Result<Record> getBlacklists(String type) {
        return Registry.WaveTactDB.select().from(BLACKLISTS).where(BLACKLISTS.TYPE.eq(type)).fetch();
    }

    public static Record getBlacklist(String type, String blacklist) {
        Result<Record> blacklists =  Registry.WaveTactDB.select().from(BLACKLISTS).where(BLACKLISTS.TYPE.eq(type)).and(BLACKLISTS.URL.eq(blacklist)).fetch();
        return getRecord(blacklists);
    }

    public static void removeBlacklist(String type, String blacklist) {
        Registry.WaveTactDB.delete(BLACKLISTS).where(BLACKLISTS.TYPE.eq(type)).and(BLACKLISTS.URL.eq(blacklist)).execute();
    }

    public static void addBlacklist(String blacklist, String type) {
        Registry.WaveTactDB.insertInto(BLACKLISTS).values(type, blacklist).execute();
    }

    public static Record getChannelProperty(String network, String channel, String property) {
        Result<Record> commandRecord = Registry.WaveTactDB.select().from(CHANNELPROPERTY).where(CHANNELPROPERTY.PROPERTY.eq(property)).and(CHANNELPROPERTY.NETWORK.eq(network)).and(CHANNELPROPERTY.CHANNEL.eq(channel)).fetch();
        return getRecord(commandRecord);

    }

    public static void updateChannelProperty(Record channelproperty){
        Registry.WaveTactDB.update(CHANNELPROPERTY).set(channelproperty).where(CHANNELPROPERTY.PROPERTY.eq(channelproperty.getValue(CHANNELPROPERTY.PROPERTY))).and(CHANNELPROPERTY.NETWORK.eq(channelproperty.getValue(CHANNELPROPERTY.NETWORK))).and(CHANNELPROPERTY.CHANNEL.eq(channelproperty.getValue(CHANNELPROPERTY.CHANNEL))).execute();
    }

    public static void addChannelProperty(String network, String channel, String property, String value) {
        Registry.WaveTactDB.insertInto(CHANNELPROPERTY).values(network, channel, property, value).execute();
    }

    public static void removeChannelProperty(String network, String channel, String property) {
        Registry.WaveTactDB.delete(CHANNELPROPERTY).where(CHANNELPROPERTY.PROPERTY.eq(property)).and(CHANNELPROPERTY.NETWORK.eq(network)).and(CHANNELPROPERTY.CHANNEL.eq(channel)).execute();
    }

    public static Record getChannelUserProperty(String network, String channel, String user, String property) {
        Result<Record> channelUserPropertyRecord = Registry.WaveTactDB.select().from(CHANNELUSERPROPERTY).where(CHANNELUSERPROPERTY.PROPERTY.eq(property)).and(CHANNELUSERPROPERTY.USER.eq(user)).and(CHANNELUSERPROPERTY.NETWORK.eq(network)).and(CHANNELUSERPROPERTY.CHANNEL.eq(channel)).fetch();
        return getRecord(channelUserPropertyRecord);

    }
    public static void updateChannelUserProperty(Record channeluserproperty){
        Registry.WaveTactDB.update(CHANNELUSERPROPERTY).set(channeluserproperty).where(CHANNELUSERPROPERTY.PROPERTY.eq(channeluserproperty.getValue(CHANNELUSERPROPERTY.PROPERTY))).and(CHANNELUSERPROPERTY.USER.eq(channeluserproperty.getValue(CHANNELUSERPROPERTY.USER))).and(CHANNELUSERPROPERTY.NETWORK.eq(channeluserproperty.getValue(CHANNELUSERPROPERTY.NETWORK))).and(CHANNELUSERPROPERTY.CHANNEL.eq(channeluserproperty.getValue(CHANNELUSERPROPERTY.CHANNEL))).execute();
    }

    public static void addChannelUserProperty(String network, String channel,String user, String property, String value) {
        Registry.WaveTactDB.insertInto(CHANNELUSERPROPERTY).values(network, channel,user, property, value).execute();
    }

    public static void removeChannelUserProperty(String network, String channel,String user, String property) {
        Registry.WaveTactDB.delete(CHANNELUSERPROPERTY).where(CHANNELUSERPROPERTY.PROPERTY.eq(property)).and(CHANNELUSERPROPERTY.USER.eq(user)).and(CHANNELUSERPROPERTY.NETWORK.eq(network)).and(CHANNELUSERPROPERTY.CHANNEL.eq(channel)).execute();
    }

    public static Result<Record> getServers() {
        return Registry.WaveTactDB.select().from(SERVERS).fetch();
    }

    public static Record getServer(String name) {
        Result<Record> serverRecord = Registry.WaveTactDB.select().from(SERVERS).where(SERVERS.NAME.eq(name)).fetch();
        return getRecord(serverRecord);
    }

    public static void updateServer(Record server){
        Registry.WaveTactDB.update(SERVERS).set(server).where(SERVERS.NAME.eq(server.getValue(SERVERS.NAME))).execute();
    }

    public static void removeServer(String name) {
        Registry.WaveTactDB.delete(SERVERS).where(SERVERS.NAME.eq(name)).execute();
    }

    public static void addServer(String name, int port, String server, String nick, String channels, String nickserv, String bindhost, boolean netadminaccess, String networkadmins, String authtype) {
        Registry.WaveTactDB.insertInto(SERVERS).values(name, port, server, nick, channels, nickserv, bindhost, netadminaccess, authtype, networkadmins).execute();
    }


}
