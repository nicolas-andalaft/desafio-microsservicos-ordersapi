package com.nicolas.ordersapi.data.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultConverter {
    public static List<Map<String, Object>> toMapList(ResultSet result) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			ResultSetMetaData metadata = result.getMetaData();
			int columnCount = metadata.getColumnCount();

			while (result.next()) {
				Map<String, Object> entry = new HashMap<String, Object>();

				for (int i = 1; i <= columnCount; i++) {
					entry.put(metadata.getColumnLabel(i), result.getObject(i));
				}

				list.add(entry);
			}
		} catch (Exception e) {}

		return list;
    }

    public static Map<String, Object> toMap(ResultSet result) {
        Map<String, Object> map = new HashMap<String, Object>();

		try {
			ResultSetMetaData metadata = result.getMetaData();
			int columnCount = metadata.getColumnCount();

			result.next();

            for (int i = 1; i <= columnCount; i++) {
                map.put(metadata.getColumnLabel(i), result.getObject(i));
            }
		} catch (Exception e) {}

		return map;
    }
}
