package com.techcavern.wavetact.utils;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

public class Configuration
{

    private boolean lock;
    private File file;
    private Map<String, String> pairs;

    public Configuration(String file)
    {
        this(new File(file));
    }

    public Configuration(File file)
    {
        this.file = file;
        pairs = new HashMap<>();
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        load();
    }

    public void load()
    {
        if (lock)
            return;
        pairs.clear();
        String line;
        String[] split;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null)
            {
                if (StringUtils.countMatches(line, "=") != 1)
                    System.out.println("Problem parsing config file for " + file.getName());
                split = line.split("=");
                pairs.put(split[0], split[1]);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // TODO: Implement saving...
    private void save()
    {
        if (lock)
            return;
    }

    public void lock()
    {
        lock = true;
    }

    public void unlock()
    {
        lock = false;
    }

    public void set(String name, String value)
    {
        if (pairs.containsKey(name))
            pairs.remove(name);
        pairs.put(name, value);
    }

    public void set(String name, Boolean value)
    {
        set(name, value.toString());
    }

    public void set(String name, Integer value)
    {
        set(name, value.toString());
    }

    public String getString(String name)
    {
        if (pairs.containsKey(name))
            return pairs.get(name);
        return null;
    }

    public boolean getBoolean(String name)
    {
        return Boolean.parseBoolean(getString(name));
    }

    public int getInteger(String name)
    {
        return Integer.parseInt(getString(name));
    }
}
