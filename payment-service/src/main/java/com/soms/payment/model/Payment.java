package com.soms.payment.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long orderId;
    private String paymentMode;
    private Double amount;
    private String status;

    private OffsetDateTime createdAt = OffsetDateTime.now();
}
