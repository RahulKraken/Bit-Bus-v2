package com.example.kraken.bit_bus_v2.Utils;

public class StringManipulation {

    public static String formatString(String s) {
        if (s.length() < 6) {
            String subString = s.substring(s.indexOf(':') + 1);
            if (subString.length() == 1) {
                s = s + "0";
            }
        }
        return s;
    }
}
