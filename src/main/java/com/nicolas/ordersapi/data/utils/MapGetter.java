package com.nicolas.ordersapi.data.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class MapGetter {
    public static Integer getInt(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : Integer.parseInt(value.toString());
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static Long getLong(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : Long.valueOf(value.toString());
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static Float getFloat(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : Float.parseFloat(value.toString());
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static BigDecimal getBigDecimal(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : BigDecimal.valueOf(Double.parseDouble(value.toString()));
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static String getString(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : value.toString();
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }  

    public static LocalDateTime getLocalDateTime(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : DateTimeFormat.fromString(value.toString());
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
