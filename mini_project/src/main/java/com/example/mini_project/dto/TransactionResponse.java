package com.example.mini_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TransactionResponse {
    public String getFraud() {
        return fraud;
    }

    public void setFraud(String fraud) {
        this.fraud = fraud;
    }

    private String fraud;


}
