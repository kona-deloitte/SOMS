package com.soms.payment.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    public Long orderId;
    public Long userId;
    public String paymentMode;
    public Double amount;
}
