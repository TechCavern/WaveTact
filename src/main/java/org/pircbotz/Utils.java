package org.pircbotz;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.pircbotz.hooks.Event;

public final class Utils {

    private Utils() {
    }

    protected static void dispatchEvent(PircBotZ bot, Event event) {
        bot.getConfiguration().getListenerManager().dispatchEvent(event);
    }

    public static int tryParseInt(String intString, int defaultValue) {
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long tryParseLong(String longString, int defaultValue) {
        try {
            return Long.parseLong(longString);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static <V> V tryGetIndex(List<V> list, int index, V defaultValue) {
        if (index < list.size()) {
            return list.get(index);
        } else {
            return defaultValue;
        }
    }

    public static void sendRawLineToServer(PircBotZ bot, String rawLine) {
        bot.sendRawLineToServer(rawLine);
    }

    public static List<String> tokenizeLine(String input) {
        List<String> stringParts = new LinkedList<>();
        if (input == null || input.length() == 0) {
            return stringParts;
        }
        String trimmedInput = input.trim();
        int pos = 0, end;
        while ((end = trimmedInput.indexOf(' ', pos)) >= 0) {
            stringParts.add(trimmedInput.substring(pos, end));
            pos = end + 1;
            if (trimmedInput.charAt(pos) == ':') {
                stringParts.add(trimmedInput.substring(pos + 1));
                return stringParts;
            }
        }
        stringParts.add(trimmedInput.substring(pos));
        return stringParts;
    }

    public static <O extends Object> Set<O> castSet(Set<? extends O> list, Class<O> cl) {
        Set<O> casted = new LinkedHashSet<>();
        for (Object o : list) {
            try {
                casted.add(cl.cast(o));
            } catch (ClassCastException e) {
            }
        }
        return casted;
    }
}
