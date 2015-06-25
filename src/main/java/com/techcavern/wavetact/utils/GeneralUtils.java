package com.techcavern.wavetact.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.util.InetAddressUtils;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.xbill.DNS.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class GeneralUtils {
    public static String buildMessage(int startint, int finishint, String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = startint; i < finishint; i++) {
            builder.append(args[i]);
            builder.append(' ');
        }
        return builder.toString().trim();
    }

    public static String prism(String toprism) {
        String prism = "";
        for (char c : toprism.replace("\n", " ").toCharArray()) {
            prism += prism(c);
        }
        return prism;
    }

    public static JsonObject getJsonObject(String url) throws Exception {
        String result = parseUrl(url);
        return new JsonParser().parse(result).getAsJsonObject();
    }
    public static JsonElement getJsonElement(String url) throws Exception {
        String result = parseUrl(url);
        return new JsonParser().parse(result);
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

    public static Record[] getRecords(String domain){
        try {
            Resolver resolver = new SimpleResolver();
            domain = domain.replace("http://", "").replace("https://", "");
            Lookup lookup = new Lookup(domain, Type.ANY);
            lookup.setResolver(resolver);
            lookup.setCache(null);
            if(lookup.getResult() == Lookup.SUCCESSFUL){
                return lookup.run();
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }

    }

    public static JsonArray getJsonArray(String url) throws Exception {
        String result = parseUrl(url);
        return new JsonParser().parse(result).getAsJsonArray();
    }

    public static String getIP(String input, PircBotX Bot, boolean IPv6Priority) {
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
                InetAddress[] addarray = InetAddress.getAllByName(IP);
                String add = "";
                if(IPv6Priority) {
                    for(InetAddress add6:addarray){
                        if(InetAddressUtils.isIPv6Address(add6.getHostAddress()))
                            add = add6.getHostAddress();
                    }
                }else{
                    for(InetAddress add4:addarray){
                        if(InetAddressUtils.isIPv4Address(add4.getHostAddress()))
                            add = add4.getHostAddress();
                    }
                }
                if (add == null || add.isEmpty()) {
                    add = InetAddress.getByName(IP).getHostAddress();
                }
                return add;
            } catch (Exception e) {
                e.printStackTrace();
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

    public static String prism(char c) {
        int number = RandomUtils.nextInt(1, 15);
        ;
        String result = null;
        switch (number) {
            case 1:
                result = Colors.BLUE + c;
                break;
            case 2:
                result = Colors.BLACK + c;
                break;
            case 3:
                result = Colors.BROWN + c;
                break;
            case 4:
                result = Colors.CYAN + c;
                break;
            case 5:
                result = Colors.DARK_BLUE + c;
                break;
            case 6:
                result = Colors.DARK_GRAY + c;
                break;
            case 7:
                result = Colors.DARK_GREEN + c;
                break;
            case 8:
                result = Colors.GREEN + c;
                break;
            case 9:
                result = Colors.LIGHT_GRAY + c;
                break;
            case 10:
                result = Colors.MAGENTA + c;
                break;
            case 11:
                result = Colors.OLIVE + c;
                break;
            case 12:
                result = Colors.PURPLE + c;
                break;
            case 13:
                result = Colors.RED + c;
                break;
            case 14:
                result = Colors.TEAL + c;
                break;
            case 15:
                result = Colors.YELLOW + c;
                break;
        }
        return result;
    }
    public static String replaceVowelsWithAccents(String original){
        if(original.contains("a"))
        original = original.replaceFirst("a", "á");
        else if(original.contains("e"))
        original = original.replaceFirst("e", "é");
        else if(original.contains("i"))
        original = original.replaceFirst("i", "í");
        else if(original.contains("o"))
            original = original.replaceFirst("o", "ó");
        else if(original.contains("u"))

            original = original.replaceFirst("u", "ú");
        else if(original.contains("y"))

            original = original.replaceFirst("y", "ý");
        else if(original.contains("A"))

            original = original.replaceFirst("A", "Á");
        else if(original.contains("E"))

            original = original.replaceFirst("E", "É");
        else if(original.contains("I"))

            original = original.replaceFirst("I", "Í");
        else if(original.contains("O"))

            original = original.replaceFirst("O", "Ó");
        else if(original.contains("U"))

            original = original.replaceFirst("U", "Ú");
        else if(original.contains("Y"))

            original = original.replaceFirst("Y","Ý");
        return original;
    }
}


