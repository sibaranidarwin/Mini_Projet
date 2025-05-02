package com.example.mini_project.model;

import jakarta.persistence.*;

import java.security.Timestamp;

@Entity
@Table(name = "user_activity_logs")
public class UserActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String activity;

    private Timestamp activityAt;

    // Getter, Setter, Constructor
}

