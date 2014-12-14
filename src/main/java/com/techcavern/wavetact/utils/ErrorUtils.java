package com.techcavern.wavetact.utils;

import org.pircbotx.User;

public class ErrorUtils {
    public static void handleException(Exception e) {
        System.out.println("> Sorry, an error occurred");
        e.printStackTrace();
    }

    public static void sendError(User user, String error) {
        user.send().notice(error);
    }
}
