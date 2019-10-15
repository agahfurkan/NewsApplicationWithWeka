package com.agah.furkan.newsapplicationwithweka.util;

public class StringUtil {
    static public String formatStringToWeka(String text) {
        text = text.replaceAll("'", "\\\\'");//handling weka runtime error
        text = text.replaceAll("\n", "");
        text = text.replaceAll("\"", "\\\\\"");
        text = text.replaceAll("\r", "");
        text = text.replaceAll("’", "\\\\’");
        text = text.replaceAll("&amp", "");
        text = text.replaceAll("&gt", "");
        if (text.contains("…")) {
            int index = text.indexOf("…");
            int whiteSpaceIndex = 0;
            for (int y = index; y >= 0; y--) {
                if (Character.isWhitespace(text.charAt(y))) {
                    whiteSpaceIndex = y;
                    break;
                }
            }
            text = text.substring(0, whiteSpaceIndex);
        }
        return text;
    }
}
