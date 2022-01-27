package com.nicolas.ordersapi.data.datasources.API;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vavr.control.Either;

public class APIDatasource {
    protected String baseUrl;

	protected Either<Exception, ?> get(String urlString, boolean expectList) {
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
            if (expectList) {
                var map = mapper.readValue(response, Map[].class);
                return Either.right(map);
            }
            else {
                var map = mapper.readValue(response, Map.class);
                return Either.right(map);
            }
            
        } catch (Exception e) {
            return Either.left(e);
        }
    }
}