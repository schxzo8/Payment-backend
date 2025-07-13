package com.paymentapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "merchants")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String merchantId;

    @Column(nullable = false)
    private String merchantName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNo;

    @Column(nullable = false)
    private String password;
}