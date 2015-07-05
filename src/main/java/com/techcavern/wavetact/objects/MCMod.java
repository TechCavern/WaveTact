package com.techcavern.wavetact.objects;

import com.google.gson.JsonObject;


public class MCMod {
    private final String version;
    private final JsonObject mod;

    public MCMod(String version, JsonObject mod) {
        this.version = version;
        this.mod = mod;
    }

    public String getVersion() {
        return this.version;
    }

    public JsonObject getMod() {
        return this.mod;
    }


}
