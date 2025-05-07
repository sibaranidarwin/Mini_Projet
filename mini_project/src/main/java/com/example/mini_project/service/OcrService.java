package com.example.mini_project.service;

import com.example.mini_project.dto.ocr.ocrRequest;
import com.example.mini_project.dto.ocr.ocrResponse;
import com.example.mini_project.model.Invoice;
import com.example.mini_project.model.InvoiceDetail;
import com.example.mini_project.model.User;
import com.example.mini_project.repository.InvoiceRepository;
import com.example.mini_project.repository.UserRepository;
import com.example.mini_project.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OcrService {

    @Value("{$ocr.api.url}")
    private static String ocr_url= "https://api.ocr.space/parse/image";

    @Value("{$ocr.api.key}")
    private static String apiKey= "K81138416688957";

    private static final Logger log = LoggerFactory.getLogger(OcrService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    public ocrResponse extractTextFromBase64(ocrRequest request) {
        ocrResponse res = new ocrResponse();
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("apikey", apiKey);
        body.add("base64Image", "data:application/pdf;base64," + request.getBase64pdf());
        body.add("language", "eng");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestData = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(ocr_url, requestData, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = null;
            try {
                root = mapper.readTree(response.getBody());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            JsonNode parsedResults = root.path("ParsedResults");

            if (parsedResults.isArray() && parsedResults.size() > 0) {
                String extractedText = parsedResults.get(0).path("ParsedText").asText();

                String[] lines = extractedText.split("\r\n|\n");

                String invoice = "";
                String tanggal = "";
                List<String> professionLines = new ArrayList<>();
                List<String> salaryLines = new ArrayList<>();

                // 1: Ambil Invoice dan Tanggal
                for (String line : lines) {
                    String lower = line.toLowerCase();
                    if (lower.contains("no.invoice") || lower.contains("no.lnvoice")) {
                        String[] parts = line.split(":");
                        if (parts.length > 1) {
                            invoice = parts[1].trim();
                            break;
                        }
                    }
                    else if (lower.contains("tanggal")) {
                        tanggal = line.replaceAll("(?i).*tanggal.*:", "").trim();
                    }
                }

                // 2: Ambil Profesi dan Gaji
                boolean foundProfesi = false;
                boolean foundGaji = false;
                for (String line : lines) {
                    String clean = line.trim();

                    if (clean.equalsIgnoreCase("Profesi/Bidang Tl")) {
                        foundProfesi = true;
                        continue;
                    }

                    if (clean.equalsIgnoreCase("Gaji (RP)")) {
                        foundGaji = true;
                        foundProfesi = false;
                        continue;
                    }

                    if (foundProfesi) {
                        professionLines.add(clean);
                    } else if (foundGaji) {
                        salaryLines.add(clean);
                    }
                }

                // 3: Gabungkan jadi objek
                List<com.example.mini_project.model.InvoiceDetail> detailList = new ArrayList<>();
                for (int i = 0; i < professionLines.size() && i < salaryLines.size(); i++) {
                    InvoiceDetail detail = new InvoiceDetail();
                    detail.setProfession(professionLines.get(i));
                    detail.setSalary(salaryLines.get(i));
                    detailList.add(detail);
                }

                // Store to db
                // Cek apakah invoice number sudah ada
                Optional<Invoice> existingInvoice = invoiceRepository.findByInvoiceNumber(invoice);

                Invoice datainv = new Invoice();
                if (existingInvoice.isPresent()) {
                    // Jika invoice ganda, set status REJECTED dan kosongkan data
                    datainv.setInvoiceNumber(null);
                    datainv.setOrderDate(null);
                    datainv.setStatus(Invoice.ApprovalStatus.REJECTED);
                    log.warn("Duplicate invoice number detected: {}", invoice);
                } else {
                    datainv.setInvoiceNumber(invoice);
                    datainv.setOrderDate(DateUtil.parseTanggal(tanggal));
                    datainv.setStatus(Invoice.ApprovalStatus.WAITING);

                    for (InvoiceDetail detail : detailList) {
                        detail.setInvoice(datainv);
                    }
                    datainv.setInvoiceDetails(detailList);
                }

                // Set waktu unggah dan user (baik valid atau ditolak tetap disimpan)
                datainv.setUploadedAt(LocalDateTime.now());

                Optional<User> user = userRepository.findByUsername(request.getUsername());
                if (user.isPresent()) {
                    datainv.setUploadedBy(user.get());
                } else {
                    log.warn("User with username {} not found", request.getUsername());
                }

                invoiceRepository.save(datainv);

                // Set ke response
                res.setResponseCode("00");
                res.setResponseMessage("Success");
                res.setInvoiceNumber(invoice);
                res.setOrderDate(tanggal);

                return res;
            } else {
                return res;
            }

        } catch (DataIntegrityViolationException e) {
            throw e;
        }
    }
}
