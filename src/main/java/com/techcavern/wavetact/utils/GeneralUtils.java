package com.techcavern.wavetact.utils;

import com.wolfram.alpha.*;

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
        List<String> waresults = new ArrayList<>();
        for (WAPod pod : queryResult.getPods()) {
            if (!pod.isError()) {
                for (WASubpod subpod : pod.getSubpods()) {
                    for (Object element : subpod.getContents()) {
                        if (element instanceof WAPlainText) {
                            waresults.add((((WAPlainText) element).getText().replaceAll("[|]", "").replaceAll("\n", ", ")).replaceAll("  ", " "));
                        }
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

}


