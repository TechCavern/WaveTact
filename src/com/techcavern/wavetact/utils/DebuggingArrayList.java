package com.techcavern.wavetact.utils;

import java.util.ArrayList;

public class DebuggingArrayList<T> extends ArrayList<T> {
    private String tagName;

    public DebuggingArrayList(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean add(T value) {
        System.out.println("[ArrayList][" + tagName + "] Adding Object: '" + value.toString() + "'");
        return super.add(value);
    }

    @Override
    public boolean remove(Object value) {
        System.out.println("[ArrayList][" + tagName + "] Removing Object: '" + value.toString() + "'");
        return super.remove(value);
    }
}
