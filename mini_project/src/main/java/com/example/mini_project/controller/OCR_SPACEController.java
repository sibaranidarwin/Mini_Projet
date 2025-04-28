package com.example.mini_project.controller;

import com.example.mini_project.dto.ocr.ocrRequest;
import com.example.mini_project.dto.ocr.ocrResponse;
import com.example.mini_project.service.Ocr_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/ocr")
public class OCR_SPACEController {

    @Autowired
    private Ocr_Service ocrService;

    @PostMapping("/submit")
    public ocrResponse Submitocr(@RequestBody ocrRequest request) {

        return ocrService.extractTextFromBase64(request);
    }
}

