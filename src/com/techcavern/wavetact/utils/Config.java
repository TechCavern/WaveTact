/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils;

/**
 *
 * @author jztech101
 */

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Config {

    private File configFile;
    private static File baseDir;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        File userDir = new File(System.getProperty("user.home"));

        baseDir = new File(userDir.getAbsolutePath() + "/.tppibot");

        if (!baseDir.exists()) {
            baseDir.mkdir();
        }
    }

    public Config(String filename) {
        this.configFile = new File(baseDir.getAbsolutePath() + "/" + filename);

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addJsonToFile(Object o) {
        String json = gson.toJson(o);

        SaveUtils.addToFile(configFile, json + "\n");
    }

    /**
     * Warning, overwrites current text if it exists
     */
    public void writeJsonToFile(Object o) {
        String json = gson.toJson(o);

        SaveUtils.saveAllToFile(configFile, json + "\n");
    }

    public String getText() {
        return SaveUtils.readTextFile(configFile);
    }

    public void writeInt(int n) {
        SaveUtils.saveAllToFile(configFile, "" + n);
    }
}
