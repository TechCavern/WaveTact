package org.pircbotz;

public enum ChatFormat {

    NORMAL("\u000f", Style.RESET),
    BOLD("\u0002", Style.FORMAT),
    UNDERLINE("\u001f", Style.FORMAT),
    REVERSE("\u0016", Style.FORMAT),
    WHITE("\u000300", Style.COLOR),
    BLACK("\u000301", Style.COLOR),
    DARK_BLUE("\u000302", Style.COLOR),
    DARK_GREEN("\u000303", Style.COLOR),
    RED("\u000304", Style.COLOR),
    BROWN("\u000305", Style.COLOR),
    PURPLE("\u000306", Style.COLOR),
    OLIVE("\u000307", Style.COLOR),
    YELLOW("\u000308", Style.COLOR),
    GREEN("\u000309", Style.COLOR),
    TEAL("\u000310", Style.COLOR),
    CYAN("\u000311", Style.COLOR),
    BLUE("\u000312", Style.COLOR),
    MAGENTA("\u000313", Style.COLOR),
    DARK_GRAY("\u000314", Style.COLOR),
    LIGHT_GRAY("\u000315", Style.COLOR);
    private final String code;
    private final Style style;

    private ChatFormat(String c, Style s) {
        code = c;
        style = s;
    }

    @Override
    public String toString() {
        return code;
    }

    public static String removeColors(String line) {
        for (ChatFormat color : ChatFormat.values()) {
            if (color.style == Style.COLOR) {
                line = line.replace(color.toString(), "");
            }
        }
        return line;
    }

    public static String removeFormatting(String line) {
        for (ChatFormat color : ChatFormat.values()) {
            if (color.style == Style.FORMAT) {
                line = line.replace(color.toString(), "");
            }
        }
        return line;
    }

    public static String removeFormattingAndColors(String line) {
        return removeFormatting(removeColors(line));
    }

    public enum Style {

        FORMAT, COLOR, RESET;
    }
}
