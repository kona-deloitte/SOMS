package com.soms.product.service;

import com.soms.product.model.Product;
import com.soms.product.repository.ProductRepository;
import com.soms.product.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found."));
    }

    public Product create(Product p) {
        return repo.save(p);
    }

    public Product update(Long id, Product p) {
        Product existing = getById(id);
        existing.setSku(p.getSku());
        existing.setName(p.getName());
        existing.setDescription(p.getDescription());
        existing.setPrice(p.getPrice());
        existing.setQuantity(p.getQuantity());
        existing.setCategory(p.getCategory());
        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ProductNotFoundException("Product not found with id: " + id);
        repo.deleteById(id);
    }
}
