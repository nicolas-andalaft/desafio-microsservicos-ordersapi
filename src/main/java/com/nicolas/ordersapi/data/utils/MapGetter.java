package com.nicolas.ordersapi.data.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

public class MapGetter {

    public static Integer getInteger(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            if (value == null) return null;
            
            return Integer.parseInt(value.toString());
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static Long getLong(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            if (value == null) return null;
            
            return Long.parseLong(value.toString());
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static String getString(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            if (value == null) return null;
            
            return value.toString();
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static Float getFloat(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            if (value == null) return null;
            
            return Float.parseFloat(value.toString());
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static BigDecimal getBigDecimal(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            if (value == null) return null;
            
            Double doubleValue = Double.parseDouble(value.toString());
            return BigDecimal.valueOf(doubleValue);
        } 
        catch (Exception e) { 
            ErrorMessage(e, key);
            return null; 
        }
    }

    public static Timestamp getTimestamp(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            if (value == null) return null;
            
            String dateString = value.toString().replace('T', ' ');
            dateString = dateString.substring(0, 23);
            return Timestamp.valueOf(dateString);
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
