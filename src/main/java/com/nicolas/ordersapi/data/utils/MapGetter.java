package com.nicolas.ordersapi.data.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

public class MapGetter {
    public static Integer getInt(Map<String, ?> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : (int)value;
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static Long getLong(Map<String, ?> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : (Long)value;
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static Float getFloat(Map<String, ?> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : (Float)value;
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static BigDecimal getBigDecimal(Map<String, ?> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : (BigDecimal)value;
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static String getString(Map<String, ?> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : (String)value;
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }  

    public static LocalDateTime getLocalDateTime(Map<String, ?> map, String key) {
        try { 
            Object value = map.get(key);
            return value == null ? null : ((Timestamp)value).toLocalDateTime();
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
