package com.nicolas.ordersapi.data.utils;

import java.util.Map;

public class MapGetter {
    public static <T> T parse(Map<String, Object> map, String key, Class<T> type) {
        try { 
            Object value = map.get(key);
            return type.cast(value);
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }
    
    private static void ErrorMessage(Exception e, String object) {
        System.out.println("Map key '" + object + "' caused error: " + e.getMessage());
    }
}
