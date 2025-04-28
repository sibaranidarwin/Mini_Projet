package com.example.mini_project.service;
import com.example.mini_project.dto.ocr.ocrRequest;
import com.example.mini_project.dto.ocr.ocrResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Ocr_Service {

    private static final String OCR_URL = "https://api.ocr.space/parse/image";
    private static final String apiKey = "K84218999688957";
    private static final Logger log = LoggerFactory.getLogger(Ocr_Service.class);

    public ocrResponse extractTextFromBase64(ocrRequest request) {
        ocrResponse res = new ocrResponse();
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("apikey", apiKey);
        body.add("base64Image", request.getBase64pdf());
        body.add("language", "eng");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(OCR_URL, requestEntity, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode parsedResults = root.path("ParsedResults");

            if (parsedResults.isArray() && parsedResults.size() > 0) {
                String extractedText = parsedResults.get(0).path("ParsedText").asText();
                log.info("Extracted Text: {}", extractedText);
                res.setExtractedText(extractedText);

                String[] lines = extractedText.split("\r\n|\n");
                String invoice = "";
                String tanggal = "";
                List<String> pesanan = new ArrayList<>();
                String totalHarga = "";
                String ongkir = "";
                String totalBayar = "";

                // Step 1: ambil invoice & tanggal dari ":" baris
                List<String> colonLines = Arrays.stream(lines)
                        .filter(line -> line.trim().startsWith(":"))
                        .map(line -> line.replace(":", "").trim())
                        .collect(Collectors.toList());

                if (colonLines.size() >= 2) {
                    invoice = colonLines.get(0);
                    tanggal = colonLines.get(1);
                }

                // Step 2: ambil item pesanan
                boolean foundTanggal = false;
                for (String line : lines) {
                    String clean = line.trim();
                    String lower = clean.toLowerCase();

                    if (!foundTanggal && clean.contains(tanggal)) {
                        foundTanggal = true;
                        continue;
                    }

                    if (foundTanggal) {
                        if (clean.length() > 3 &&
                                clean.matches(".*[a-zA-Z]+.*") &&
                                !lower.contains("total") &&
                                !lower.contains("ongkir") &&
                                !lower.contains("resi") &&
                                !lower.contains("estimasi") &&
                                !lower.contains("tujuan") &&
                                !lower.contains("ekspedisi") &&
                                !lower.contains("harga")) {
                            pesanan.add(clean);
                        }

                        if (lower.contains("detail ekspedisi") || lower.contains("jna") || lower.contains("no. resi")) {
                            break;
                        }
                    }
                }

                // Step 3: ambil angka-angka dari bawah untuk total harga & ongkir
                List<String> reversed = new ArrayList<>(Arrays.asList(lines));
                Collections.reverse(reversed);

                List<String> angkaList = reversed.stream()
                        .filter(line -> line.trim().matches(".*\\d{2,3}[ .]?\\d{3}[.,]?\\d{0,3}.*")) // deteksi angka besar
                        .collect(Collectors.toList());

                if (angkaList.size() >= 3) {
                    totalBayar = angkaList.get(0).trim();
                    ongkir = angkaList.get(1).trim();
                    totalHarga = angkaList.get(2).trim();
                }

                // Set hasil
                res.setInvoiceNumber(invoice);
                res.setOrderDate(tanggal);
                res.setItems(pesanan);
                res.setTotalHargaProduk(totalHarga);
                res.setOngkosKirim(ongkir);
                res.setTotalBayar(totalBayar);

                log.info("Data ocr {}", res);

                return res;

            } else {
                res.setExtractedText("No text found");
                return res;
            }

        } catch (Exception e) {
            log.error("Error OCR: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to extract text", e);
        }
    }

}
