package com.soms.product.bootstrap;

import com.soms.product.model.Product;
import com.soms.product.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository repo;

    public DataLoader(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {
            repo.save(new Product("SKU-001", "Apple iPhone 14", "Smartphone", new BigDecimal("699.99"), 10, "Electronics"));
            repo.save(new Product("SKU-002", "Sony WH-1000XM4", "Headphones", new BigDecimal("299.99"), 15, "Electronics"));
        }
    }
}
