package com.example.mini_project.dto.ocr;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ocrResponse {

        private String invoiceNumber;
        private String orderDate;
        private List<String> items;
        private String extractedText;
        private String totalHargaProduk;
        private String ongkosKirim;
        private String totalBayar;


    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }

    public String getTotalHargaProduk() {
        return totalHargaProduk;
    }

    public void setTotalHargaProduk(String totalHargaProduk) {
        this.totalHargaProduk = totalHargaProduk;
    }

    public String getOngkosKirim() {
        return ongkosKirim;
    }

    public void setOngkosKirim(String ongkosKirim) {
        this.ongkosKirim = ongkosKirim;
    }

    public String getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(String totalBayar) {
        this.totalBayar = totalBayar;
    }
}
