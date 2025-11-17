package com.soms.order.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class OrderResponse {
    public Long id;
    public Long userId;
    public String userName;
    public String email;
    public String phone;
    public Long productId;
    public int quantity;
    public double totalAmount;
    public String paymentStatus;
    public OffsetDateTime createdAt;
    public String discountApplied;
    public String message;
}
