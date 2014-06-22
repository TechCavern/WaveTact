package com.techcavern.wavetact.utils;

public class ErrorUtils {
    public static void handleException(Exception e) {
        System.out.println("> Sorry, an error occurred");
        e.printStackTrace();
    }
}
