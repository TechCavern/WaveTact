package com.techcavern.wavetact.utils.file;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Written by logangorence
 */
public class Configuration {

    private final File file;
    private final Map<String, String> pairs;
    private boolean lock;

    public Configuration(String file) throws ConfigurationException {
        this(new File(file));
    }

    public Configuration(File file) throws ConfigurationException {
        this.file = file;
        pairs = new HashMap<>();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        load();
    }

    public void load() throws ConfigurationException {
        if (lock)
            return;
        pairs.clear();
        String line;
        String[] split;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int lineNum = 1;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#"))
                    continue;
                if (StringUtils.countMatches(line, "=") != 1)
                    System.out.println("Problem parsing config file for " + file.getName());
                split = line.split("=");
                try {
                    pairs.put(split[0], split[1]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new ConfigurationException("Syntax error on line " + lineNum);
                }
                lineNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        if (lock)
            return;
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            for (String s : pairs.keySet()) {
                writer.println(s + "=" + pairs.get(s));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lock() {
        lock = true;
    }

    public void unlock() {
        lock = false;
    }

    public void set(String name, String value) {
        if (lock)
            return;
        if (pairs.containsKey(name))
            pairs.remove(name);
        pairs.put(name, value);
    }

    public void set(String name, Boolean value) {
        set(name, value.toString());
    }

    public void set(String name, Integer value) {
        set(name, value.toString());
    }

    public String getString(String name) {
        if (pairs.containsKey(name))
            return pairs.get(name);
        return null;
    }

    public boolean getBoolean(String name) {
        return Boolean.parseBoolean(getString(name));
    }

    public int getInteger(String name) {
        return Integer.parseInt(getString(name));
    }

    public boolean exists(String name) {
        return pairs.containsKey(name);
    }

    public class ConfigurationException extends Throwable {
        public ConfigurationException(String message) {
            super(message);
        }
    }
}
