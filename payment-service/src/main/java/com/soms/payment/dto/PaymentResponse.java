package com.soms.payment.dto;

import java.time.OffsetDateTime;

public class PaymentResponse {
    public Long id;
    public Long orderId;
    public String paymentMode;
    public Double amount;
    public String status;
    public OffsetDateTime createdAt;
}
