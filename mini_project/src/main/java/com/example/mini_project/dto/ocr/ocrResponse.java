package com.example.mini_project.dto.ocr;

import com.example.mini_project.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ocrResponse extends BaseResponse {

        private String invoiceNumber;
        private String orderDate;


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
}
