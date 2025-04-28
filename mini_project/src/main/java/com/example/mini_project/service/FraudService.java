package com.example.mini_project.service;

import com.example.mini_project.dto.TransactionRequest;
import com.example.mini_project.dto.TransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FraudService {

    private static final Logger log = LoggerFactory.getLogger(FraudService.class);

    public TransactionResponse calculateRisk(TransactionRequest request) {
        var response = new TransactionResponse();
        double score = 0.0;

        log.info("request {}", request.getAmount());
        if (request.getAmount() > 10_000_000) score += 0.5;
        if (!request.getLocation().equalsIgnoreCase(request.getUserLocation())) score += 0.3;
        if (!request.getDeviceId().equalsIgnoreCase(request.getUserdeviceId())) score += 0.2;

        response.setFraud(String.valueOf(Math.min(score, 1.0)));

        return response;
    }
}
