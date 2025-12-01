package com.soms.payment.dto;

import lombok.Data;

import java.time.Instant;
import java.time.OffsetDateTime;

@Data
public class PaymentResponse {
    public Long id;
    public Long orderId;
    public Long userId;
    public String userName;
    public String paymentMode;
    public double amount;
    public String status;
    public OffsetDateTime createdAt;
}
