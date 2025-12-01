package com.soms.order.dto;

import lombok.Data;

@Data
public class ProductResponse {
    public Long id;
    public String name;
    public Double price;
}
