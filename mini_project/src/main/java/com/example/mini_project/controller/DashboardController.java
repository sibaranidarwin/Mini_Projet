package com.example.mini_project.controller;


import com.example.mini_project.dto.approval.InvoiceDTO;
import com.example.mini_project.dto.approval.InvoiceResponse;
import com.example.mini_project.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @PostMapping("/list")
    public InvoiceResponse<List<InvoiceDTO>> getallinvoice() {
        return dashboardService.getAllInvoice();
    }

}
