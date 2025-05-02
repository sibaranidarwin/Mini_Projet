package com.example.mini_project.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;

    private String invoiceNumber;

    private LocalDate orderDate;

    private String vendorName;

    private BigDecimal totalAmount;

    @Column(columnDefinition = "TEXT")
    private String ocrRawText;

    private Timestamp uploadedAt;

    // getter, setter, constructor
}

