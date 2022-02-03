package com.nicolas.ordersapi.data.datasources.API;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vavr.collection.List;
import io.vavr.control.Either;

public class APIDatasource {
    protected String baseUrl;

    @SuppressWarnings("unchecked")
	protected Either<Exception, List<Map<String, Object>>> get(String urlString, boolean expectList) {
		try {
            var url = new URL(urlString);
            var conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200)
                return Either.left(new Exception("Network error status code " + responseCode));

            String response = "";
            var scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                response += scanner.nextLine();
            }
            scanner.close();

            var mapper = new ObjectMapper();
            java.util.List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

            if (expectList) {
                var list = mapper.readValue(response, java.util.List.class);
                result = list;
            }
            else {
                var map = mapper.readValue(response, Map.class);
                result.add(map);
            }

            return Either.right(List.ofAll(result));
            
        } catch (Exception e) {
            return Either.left(e);
        }
    }
}