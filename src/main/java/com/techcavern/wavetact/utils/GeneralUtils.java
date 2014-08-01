package com.techcavern.wavetact.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wolfram.alpha.*;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GeneralUtils {
    public static List<String> getWAResult(String input) throws Exception {
        WAEngine engine = new WAEngine();
        engine.setAppID("H3G288-R6ULG68XJW");
        engine.addFormat("plaintext");
        WAQuery query = engine.createQuery();
        query.setInput(input);
        WAQueryResult queryResult = engine.performQuery(query);
        List<String> waResults = new ArrayList<>();
        for (WAPod pod : queryResult.getPods()) {
            for (WASubpod spod : pod.getSubpods()) {
                for (Object e : spod.getContents()) {
                    if (e instanceof WAPlainText) {
                        waResults.add(((WAPlainText) e).getText().replaceAll("[|]", "").replaceAll("\n", ", ").replaceAll("  ", " "));
                    }
                }
            }
        }
        return waResults;
    }

    public static String buildMessage(int startint, int finishint, String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = startint; i < finishint; i++) {
            builder.append(args[i]);
            builder.append(' ');
        }
        return builder.toString().trim();
    }


    public static JsonObject getJsonObject(String url) throws Exception {
        URL url1 = new URL(url);
        String line;
        String result = "";
        BufferedReader buffereader = new BufferedReader(new InputStreamReader(url1.openStream()));
       while ((line = buffereader.readLine()) != null) {
            result += line.replaceAll("\n", " ") + "\n";
        }
        buffereader.close();
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
    public static JsonArray getJsonArray(String url) throws Exception {
        URL url1 = new URL(url);
        String line;
        String result = "";
        BufferedReader buffereader = new BufferedReader(new InputStreamReader(url1.openStream()));
        while ((line = buffereader.readLine()) != null) {
            result += line.replaceAll("\n", " ") + "\n";
        }
        buffereader.close();
        return new JsonParser().parse(result).getAsJsonArray();
    }

    public static String getWADidYouMeans(String input) throws Exception {
        WAEngine engine = new WAEngine();
        engine.setAppID("H3G288-R6ULG68XJW");
        engine.addFormat("plaintext");
        WAQuery query = engine.createQuery();
        query.setInput(input);
        WAQueryResult queryResult = engine.performQuery(query);
        return StringUtils.join(queryResult.getDidYouMeans(), ",");
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


