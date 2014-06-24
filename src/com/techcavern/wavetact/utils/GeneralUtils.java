package com.techcavern.wavetact.utils;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.google.gson.JsonObject;
import org.apache.http.impl.client.SystemDefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by jztech101 on 6/23/14.
 */
public class GeneralUtils {
    public static WAQueryResult getWAResult(String input) throws Exception {

        WAEngine engine = new WAEngine();
        engine.setAppID("H3G288-R6ULG68XJW");
        engine.addFormat("plaintext");
        WAQuery query = engine.createQuery();
        query.setInput(input);
        WAQueryResult queryResult = engine.performQuery(query);
        return queryResult;

    }
    public static String buildMessage(int g,int p, String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = g; i < p; i++) {
            builder.append(args[i]);
            builder.append(' ');
        }
        return builder.toString().trim();
    }
}


