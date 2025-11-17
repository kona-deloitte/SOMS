package com.soms.payment.dto;

import java.time.Instant;
import java.time.OffsetDateTime;

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
