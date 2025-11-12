package com.example.productos_api.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class TursoClient {

    @Value("${turso.url}")
    private String tursoUrl;

    @Value("${turso.token}")
    private String tursoToken;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode executeQuery(String sql, List<Object> params) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(tursoToken);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("statements", List.of(Map.of("q", sql, "params", params != null ? params : List.of())));

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    tursoUrl,
                    request,
                    String.class
            );

            return objectMapper.readTree(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Error executing Turso query: " + e.getMessage(), e);
        }
    }
}
