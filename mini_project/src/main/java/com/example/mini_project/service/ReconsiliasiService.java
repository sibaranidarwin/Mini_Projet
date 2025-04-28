package com.example.mini_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class ReconsiliasiService {

        @Autowired
        private final RestTemplate restTemplate;

        @Value("${replicate.api.key}")
        private String apiKey;

        public ReconsiliasiService(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public String callReplicate(String prompt) {
            String url = "https://api.replicate.com/v1/predictions";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            String modelVersion = "8beff3369e81422112d93b89ca01426147de542cd4684c244b673b105188fe5f";

            Map<String, Object> input = Map.of("prompt", prompt);
            Map<String, Object> body = Map.of(
                    "version", modelVersion,
                    "input", input
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            try {
                ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
                return response.getBody();
            } catch (Exception ex) {
                return "Error while calling Replicate API: " + ex.getMessage();
            }
        }
    }

