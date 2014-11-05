package com.techcavern.wavetact.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.util.InetAddressUtils;
import org.pircbotx.PircBotX;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

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
    public static String getJsonString(JsonArray array, String name){
        String returning = "";
        for(int i = 0; i < array.size(); i++){
            if(i == 0 ){
                returning = array.get(i).getAsJsonObject().get(name).getAsString();
            }else{
                returning += ", " +array.get(i).getAsJsonObject().get(name).getAsString();
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
        if (IP == null || IP.replaceFirst(":", "").contains(":")) {
            return null;
        } else if (InetAddressUtils.isIPv4Address(IP) || InetAddressUtils.isIPv6Address(IP)) {
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

    public static String[] toArray(String input) {
        return StringUtils.split(input, " ");
    }


}


