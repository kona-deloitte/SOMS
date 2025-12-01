package com.soms.order.dto;

import lombok.Data;

@Data
public class OrderRequest {
    public Long userId;
    public Long productId;
    public Integer quantity;
    public String paymentMode;
}
