package com.techcavern.wavetact.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.util.InetAddressUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.xbill.DNS.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.techcavern.wavetactdb.Tables.BLACKLISTS;

public class GeneralUtils {
    public static String buildMessage(int startint, int finishint, String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = startint; i < finishint; i++) {
            builder.append(args[i]);
            builder.append(' ');
        }
        return builder.toString().trim();
    }


    public static JsonObject getJsonObject(String url) throws Exception {
        String result = parseUrl(url);
        return new JsonParser().parse(result).getAsJsonObject();
    }

    public static String getJsonString(JsonArray array, String name) {
        String returning = "";
        for (int i = 0; i < array.size(); i++) {
            if (i == 0) {
                returning = array.get(i).getAsJsonObject().get(name).getAsString();
            } else {
                returning += ", " + array.get(i).getAsJsonObject().get(name).getAsString();
            }
        }
        return returning;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String parseUrl(String Url) throws Exception {
        URL url1 = new URL(Url);
        String line;
        String result = "";
        BufferedReader buffereader = new BufferedReader(new InputStreamReader(url1.openStream()));
        while ((line = buffereader.readLine()) != null) {
            result += line.replaceAll("\n", " ") + "\n";
        }
        buffereader.close();
        return result;

    }

    public static JsonArray getJsonArray(String url) throws Exception {
        String result = parseUrl(url);
        return new JsonParser().parse(result).getAsJsonArray();
    }

    public static String getIP(String input, PircBotX Bot) {
        String IP = "";
        if (input.contains(".") || input.contains(":")) {
            IP = input;
        } else {
            IP = IRCUtils.getHost(Bot, input);
        }
        if (InetAddressUtils.isIPv4Address(IP) || InetAddressUtils.isIPv6Address(IP)) {
            return IP;
        } else {
            IP = IP.replaceAll("http://|https://", "");
            try {
                return InetAddress.getByName(IP).getHostAddress();
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static long getMilliSeconds(String input) {
        if (input.toLowerCase().endsWith("h")) {
            return Long.parseLong(input.replace("h", "")) * 60 * 60 * 1000;
        } else if (input.toLowerCase().endsWith("m")) {
            return Long.parseLong(input.replace("m", "")) * 60 * 1000;

        } else if (input.toLowerCase().endsWith("d")) {
            return Long.parseLong(input.replace("d", "")) * 24 * 60 * 60 * 1000;

        } else if (input.toLowerCase().endsWith("w")) {
            return Long.parseLong(input.replace("w", "")) * 7 * 24 * 60 * 60 * 1000;

        } else if (input.toLowerCase().endsWith("s")) {
            return Long.parseLong(input.replace("s", "")) * 1000;

        } else {
            return Long.parseLong(input);
        }
    }

    public static String[] removeColorsAndFormatting(String[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = Colors.removeFormattingAndColors(input[i]);
        }
        return input;
    }

    public static String[] toArray(String input) {
        return StringUtils.split(input, " ");
    }

    public static void checkBlacklist(User user, String[] args, PircBotX network, Channel channel, String prefix, String type) throws Exception{
        String BeforeIP = GeneralUtils.getIP(args[0], network);
        if (BeforeIP == null) {
            ErrorUtils.sendError(user, "Invalid ip/user");
            return;
        } else if (BeforeIP.contains(":")) {
            ErrorUtils.sendError(user, "IPv6 is not supported");
            return;
        }
        String[] IPString = StringUtils.split(BeforeIP, ".");
        String IP = "";
        for (int i = IPString.length - 1; i > -1; i--) {
            if (IP.isEmpty()) {
                IP = IPString[i];
            } else {
                IP += "." + IPString[i];
            }
        }
        Boolean sent = false;
        Resolver resolver = new SimpleResolver();
        Result<Record> blacklist = DatabaseUtils.getBlacklists(type);
        if (blacklist.isEmpty()) {
            ErrorUtils.sendError(user, "No "+type+" blacklists found in database");
            return;
        }
        for (org.jooq.Record Blacklist : blacklist) {
            Lookup lookup = new Lookup(IP + "." + Blacklist.getValue(BLACKLISTS.URL), Type.ANY);
            lookup.setResolver(resolver);
            lookup.setCache(null);
            org.xbill.DNS.Record[] records = lookup.run();
            if (lookup.getResult() == lookup.SUCCESSFUL) {
                IRCUtils.sendMessage(user, network, channel, BeforeIP + " found in " + Blacklist.getValue(BLACKLISTS.URL), prefix);
                sent = true;
                for (org.xbill.DNS.Record rec : records) {
                    if (rec instanceof TXTRecord) {
                        IRCUtils.sendMessage(user, network, channel, Type.string(rec.getType()) + " - " + StringUtils.join(rec, " "), prefix);
                    }
                }
            }
        }
        if (!sent) {
            IRCUtils.sendMessage(user, network, channel, BeforeIP + " not found in "+type+" blacklists", prefix);
        }
    }
    public static void modifyBlacklist(User user, PircBotX network, Channel channel, String[] args, String prefix,String type){
        if (args.length > 0) {
            if (args[0].startsWith("-")) {
                Record blacklist = DatabaseUtils.getBlacklist(type, args[0].replaceFirst("\\-", "").replaceAll("http://|https://", ""));
                if (blacklist != null) {
                    DatabaseUtils.removeBlacklist(type,args[0].replaceFirst("\\-", "").replaceAll("http://|https://", ""));
                    IRCUtils.sendMessage(user, network, channel, type + " blacklist removed", prefix);
                } else {
                    ErrorUtils.sendError(user, type + " blacklist does not exist on list");
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                List<String> blacklists = new ArrayList<>();
                for(Record bl:DatabaseUtils.getBlacklists(type))
                    blacklists.add(bl.getValue(BLACKLISTS.URL));
                if (!blacklists.isEmpty()) {
                    IRCUtils.sendMessage(user, network, channel, StringUtils.join(blacklists, ", "), prefix);
                } else {
                    ErrorUtils.sendError(user, type + " blacklist is empty");
                }
            } else {
                Record blacklist = DatabaseUtils.getBlacklist(type, args[0].replaceFirst("\\-", "").replaceAll("http://|https://", ""));
                if (blacklist == null) {
                    DatabaseUtils.addBlacklist(args[0].replaceFirst("\\-", "").replaceAll("http://|https://", ""), type);
                    IRCUtils.sendMessage(user, network, channel, type + " blacklist added", prefix);
                } else {
                    ErrorUtils.sendError(user, type + " blacklist is already listed");
                }
            }
        } else {
            ErrorUtils.sendError(user, "Please specify a "+type+" blacklist");
        }
    }
}


