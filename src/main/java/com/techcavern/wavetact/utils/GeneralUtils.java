package com.techcavern.wavetact.utils;

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
        List<String> waresults = new ArrayList<String>();
        for (WAPod pod : queryResult.getPods()) {
            for (WASubpod spod : pod.getSubpods()) {
                for (Object e : spod.getContents()) {
                    if (e instanceof WAPlainText) {
                        waresults.add(((WAPlainText) e).getText().replaceAll("[|]", "").replaceAll("\n", ", ").replaceAll("  ", " "));
                        }
                    }


                }
        }
        return waresults;

    }

    public static String buildMessage(int g, int p, String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = g; i < p; i++) {
            builder.append(args[i]);
            builder.append(' ');
        }
        return builder.toString().trim();
    }

    public static JsonObject getJsonObject(String s) throws Exception {
        URL h = new URL(s);
        BufferedReader f = new BufferedReader(new InputStreamReader(h.openStream()));
        String g = f.readLine().replaceAll("\n", " ");
        return new JsonParser().parse(g).getAsJsonObject();
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
    public static long getMilliSeconds(String c){
        if(c.toLowerCase().endsWith("h")){
            return Integer.parseInt(c.replace("h", ""))*60*60*1000;
        }else if(c.toLowerCase().endsWith("m")){
            return Integer.parseInt(c.replace("m", ""))*60*1000;

        }else if(c.toLowerCase().endsWith("d")){
            return Integer.parseInt(c.replace("d", ""))*24*60*60*1000;

        }else if(c.toLowerCase().endsWith("w")){
            return Integer.parseInt(c.replace("w", ""))*7*24*60*60*1000;

        }else if(c.toLowerCase().endsWith("s")){
            return Integer.parseInt(c.replace("s", ""))*1000;

        }else{
            return Integer.parseInt(c);
        }
    }
    public static String[] toArray(String i){
        return StringUtils.split(i, " ");
    }

}


