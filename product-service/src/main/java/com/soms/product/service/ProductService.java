package com.soms.product.service;

import com.soms.product.model.Product;
import com.soms.product.repository.ProductRepository;
import com.soms.product.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


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

//
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ProductNotFoundException("Product not found with id: " + id);
        repo.deleteById(id);
    }

    @Transactional

    public Product reduceQuantity(Long id, int quantity) {
        Product p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (p.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock!");
        }
        p.setQuantity(p.getQuantity() - quantity);
        return repo.save(p);
    }

    @Transactional
    public Product update(Long id, Product p) {
        Product existing = getById(id);

        existing.setSku(p.getSku() != null ? p.getSku() : existing.getSku());
        existing.setName(p.getName() != null ? p.getName() : existing.getName());
        existing.setDescription(p.getDescription() != null ? p.getDescription() : existing.getDescription());
        existing.setPrice(p.getPrice() != null ? p.getPrice() : existing.getPrice());
        existing.setQuantity(p.getQuantity() != null ? p.getQuantity() : existing.getQuantity());
        existing.setCategory(p.getCategory() != null ? p.getCategory() : existing.getCategory());

        return repo.save(existing);
    }



    @Transactional
    public Product updatePartial(Long id, Map<String, Object> updates) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "price" -> product.setPrice(new BigDecimal(value.toString()));
                case "quantity" -> product.setQuantity(Integer.parseInt(value.toString()));
                case "name" -> product.setName(value.toString());
                case "description" -> product.setDescription(value.toString());
                case "category" -> product.setCategory(value.toString());
                case "sku" -> product.setSku(value.toString());
            }
        });

        return repo.save(product);
    }


}
