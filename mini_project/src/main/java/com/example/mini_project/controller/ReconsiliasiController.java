package com.example.mini_project.controller;

import com.example.mini_project.service.ReconsiliasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

    @RestController
    public class ReconsiliasiController {

        @Value("${replicate.api.key}") // Store your API Key in application.properties
        private String replicateApiKey;

        private final ReconsiliasiService reconsiliasiService;

        public ReconsiliasiController(ReconsiliasiService reconsiliasiService) {
            this.reconsiliasiService = reconsiliasiService;
        }

        @PostMapping("/predict")
        public String predict(@RequestBody String prompt) {
            return reconsiliasiService.callReplicate(prompt);
        }
    }


