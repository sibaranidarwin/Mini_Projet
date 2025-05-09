package com.example.mini_project.controller;

import com.example.mini_project.dto.ocr.ocrRequest;
import com.example.mini_project.dto.ocr.ocrResponse;
import com.example.mini_project.service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @PostMapping("/submit")
    public ocrResponse Submitocr(@RequestBody ocrRequest request) {
        return ocrService.extractTextFromBase64(request);
    }
}

