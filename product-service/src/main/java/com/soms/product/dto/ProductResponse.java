package com.soms.product.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductResponse {
    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String category;
}
