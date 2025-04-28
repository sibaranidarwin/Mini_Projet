package com.example.mini_project.controller;


import com.example.mini_project.dto.TransactionRequest;
import com.example.mini_project.dto.TransactionResponse;
import com.example.mini_project.service.FraudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FraudController {

    @Autowired
    FraudService fraudService;

    @PostMapping("/fraud")
    public TransactionResponse transaction (@RequestBody TransactionRequest request){

    return fraudService.calculateRisk(request);
}

}
