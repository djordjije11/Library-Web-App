package com.djordjije11.libraryappapi.helper.string.util;

public final class StringExt {
    public final static String EMPTY = "";

    public static boolean isNullOrBlank(String text){
        if(text == null){
            return true;
        }
        return text.isBlank();
    }

    public static boolean isNotNullNorBlank(String text){
        return !isNullOrBlank(text);
    }
}
