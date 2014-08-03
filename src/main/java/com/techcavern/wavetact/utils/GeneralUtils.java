package com.techcavern.wavetact.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wolfram.alpha.*;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.PircBotX;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static String parseUrl(String Url) throws Exception{
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
    public static String getIP(String input, PircBotX Bot){
        String IP = "";
        if (input.contains(".") || input.contains(":")) {
            IP = input;
        } else {
            IP = IRCUtils.getHost(Bot, input);
        }
        if(IP == null || IP.replaceFirst(":", "").contains(":")){
           IP = null;
        }else {
            Long time = System.currentTimeMillis();
            IP = IP.replaceAll("http://|https://", "");
            try {
                Socket socket = new Socket(InetAddress.getByName(IP), 80);
                IP = socket.getInetAddress().getHostAddress();
                socket.close();
                if(IP.contains(":")){
                    return null;
                }
            }catch(Exception e){
                IP = null;
            }
        }
        return IP;
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


