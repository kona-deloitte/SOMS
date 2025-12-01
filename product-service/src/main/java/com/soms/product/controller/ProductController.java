package com.soms.product.controller;

import com.soms.product.model.Product;
import com.soms.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.ok(service.create(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(service.update(id, product));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updatePartial(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Product updated = service.updatePartial(id, updates);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/addStock")
    public ResponseEntity<Product> addStock(@PathVariable Long id, @RequestParam int amount, @RequestHeader(name="X-User-Id", required=false) Long userId) {
        Product updated = service.addStock(id, amount, userId);
        return ResponseEntity.ok(updated);
    }


    @PutMapping("/{id}/reduce")
    public ResponseEntity<Product> reduceQuantity(@PathVariable Long id, @RequestParam int quantity) {
        return ResponseEntity.ok(service.reduceQuantity(id, quantity));
    }
}
