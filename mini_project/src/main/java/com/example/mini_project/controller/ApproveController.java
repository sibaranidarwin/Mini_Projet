package com.example.mini_project.controller;

import com.example.mini_project.dto.BaseResponse;
import com.example.mini_project.dto.approval.ApproveRequest;
import com.example.mini_project.dto.approval.InvoiceDTO;
import com.example.mini_project.dto.approval.InvoiceResponse;
import com.example.mini_project.service.ApprovedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approve")
public class ApproveController {

    @Autowired
    private ApprovedService approvedService;

    @GetMapping("/list")
    public InvoiceResponse<List<InvoiceDTO>> getWaitingInvoices() {
        return(approvedService.getAllWaitingInvoices());
    }

    @PostMapping("/update")
    public BaseResponse approveInvoice(@RequestBody ApproveRequest request){
     return approvedService.approveInvoice(request);
    }
}
