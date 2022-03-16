package com.nicolas.ordersapi.data.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapGetter {
    private static Logger logger = Logger.getLogger("Logger");

    private MapGetter() {}

    public static Integer getInteger(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            if (value == null) return null;
            
            return Integer.parseInt(value.toString());
        } 
        catch (Exception e) { 
            errorMessage(e, key);
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
            errorMessage(e, key);
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
            errorMessage(e, key);
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
            errorMessage(e, key);
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
            errorMessage(e, key);
            return null; 
        }
    }

    public static Timestamp getTimestamp(Map<String, Object> map, String key) {
        try { 
            Object value = map.get(key);
            if (value == null) return null;
            
            String dateString = value.toString().replace('T', ' ');
            dateString = dateString.substring(0, 24);
            return Timestamp.valueOf(dateString);
        } 
        catch (Exception e) { 
            errorMessage(e, key);
            return null; 
        }
    }
    
    private static void errorMessage(Exception e, String object) {
        var message = String.format("Map key '%s' caused error: %s", object, e.getMessage());
        logger.log(Level.FINE, message);
    }
}