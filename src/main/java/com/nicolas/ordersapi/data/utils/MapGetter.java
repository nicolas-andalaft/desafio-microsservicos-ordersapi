package com.nicolas.ordersapi.data.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

public class MapGetter {
    public static Integer getInt(Map<String, ?> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : Integer.parseInt(String.valueOf(value));
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static Long getLong(Map<String, ?> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : Long.valueOf(String.valueOf(value));
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static Float getFloat(Map<String, ?> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : Float.parseFloat(String.valueOf(value));
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static BigDecimal getBigDecimal(Map<String, ?> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : BigDecimal.valueOf(Long.parseLong(String.valueOf(value)));
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static String getString(Map<String, ?> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : String.valueOf(value);
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }  

    public static LocalDateTime getLocalDateTime(Map<String, ?> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : (Timestamp.valueOf(String.valueOf(value))).toLocalDateTime();
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
