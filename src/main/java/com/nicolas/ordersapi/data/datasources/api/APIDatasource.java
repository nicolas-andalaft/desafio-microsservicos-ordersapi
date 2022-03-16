package com.nicolas.ordersapi.data.datasources.api;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vavr.collection.List;
import io.vavr.control.Either;

public class APIDatasource {
    protected String baseUrl;

    @SuppressWarnings("unchecked")
	protected Either<Exception, List<Map<String, Object>>> execute(String urlString, Map<String, Object> body, boolean expectList) {
        java.util.List<Map<String, Object>> result = new ArrayList<>();

        try {
            String response = connect(urlString, body);

            var mapper = new ObjectMapper();
            
            if (expectList) {
                var list = mapper.readValue(response, java.util.List.class);
                result = list;
            }
            else {
                var map = mapper.readValue(response, Map.class);
                result.add(map);
            }
        }
        catch (Exception e) {
            return Either.left(e);
        }

        return Either.right(List.ofAll(result));
    }

    private String connect(String urlString, Map<String, Object> body) throws IOException {
        
        var url = new URL(urlString);
        var conn = (HttpURLConnection) url.openConnection();

        if (body == null) {
            conn.setRequestMethod("GET");
        }
        else {
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = new ObjectMapper().writeValueAsString(body);
            
            var stream = conn.getOutputStream();
            var streamWriter = new OutputStreamWriter(stream, StandardCharsets.UTF_8);

            streamWriter.write(jsonInputString);
            streamWriter.flush();
            streamWriter.close();
            stream.close();
        }

        conn.connect();
                
        int responseCode = conn.getResponseCode();
        if (responseCode != 200)
            throw new IOException("Network error status code " + responseCode);

        var response = new StringBuilder();
        var scanner = new Scanner(conn.getInputStream());

        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        return response.toString();
    }
}