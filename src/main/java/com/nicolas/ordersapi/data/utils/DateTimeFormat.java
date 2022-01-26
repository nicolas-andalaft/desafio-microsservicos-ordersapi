package com.nicolas.ordersapi.data.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormat {
    private static final String pattern = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String toString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime fromString(String dateTime) {
        LocalDateTime result;
        try {
            result = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            try {
                result = LocalDateTime.parse(dateTime);
            } catch (Exception ex) {
                result = null;
            }
        }
        return result;
    }
}
