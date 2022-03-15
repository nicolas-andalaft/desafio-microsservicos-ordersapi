package com.nicolas.ordersapi.data.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.vavr.collection.List;


public class ResultConverter {
    public static List<Map<String, Object>> toMapList(ResultSet result) {
        var list = new ArrayList<Map<String, Object>>();
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

		return List.ofAll(list);
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
