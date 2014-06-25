package com.techcavern.wavetact.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Constants {
    public static final Gson GSON = new GsonBuilder().create();
    public static final Gson GSON_PRETTY_PRINT = new GsonBuilder().setPrettyPrinting().create();
}
