package com.soms.order.feign;

import com.soms.order.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {
    @GetMapping("/products/{id}")
    ProductResponse getProductById(@PathVariable Long id);

    @PutMapping("/products/{id}/reduce")
    ProductResponse reduceProductQuantity(@PathVariable Long id, @RequestParam int quantity);
}

