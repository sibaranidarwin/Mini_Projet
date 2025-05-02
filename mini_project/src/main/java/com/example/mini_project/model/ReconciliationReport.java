package com.example.mini_project.model;

import jakarta.persistence.*;

import java.security.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "reconciliation_reports")
public class ReconciliationReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reportDate = LocalDate.now();

    @Column(columnDefinition = "JSON")
    private String reportData;

    @ManyToOne
    @JoinColumn(name = "generated_by")
    private User generatedBy;

    private Timestamp generatedAt;

    // Getter, Setter, Constructor
}

