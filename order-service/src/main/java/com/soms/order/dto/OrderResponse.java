package com.soms.order.dto;

import java.time.OffsetDateTime;

public class OrderResponse {
    public Long id;
    public Long productId;
    public Integer quantity;
    public Double totalAmount;
    public String paymentStatus;
    public OffsetDateTime createdAt;
}
