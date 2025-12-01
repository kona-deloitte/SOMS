package com.soms.order.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    public Long orderId;
    public String paymentMode;
    public Double amount;
    public Long userId;
}
