package com.nimbleways.springboilerplate.services.strategy;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.services.implementations.ProductService;

@Component
public class ExpirableProductProcessingStrategy implements ProductProcessingStrategy {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ExpirableProductProcessingStrategy(ProductService productService,
                                              ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public String getType() {
        return "EXPIRABLE";
    }

    @Override
    public void process(Product product) {
        if (product.getAvailable() > 0 && product.getExpiryDate().isAfter(LocalDate.now())) {
            product.setAvailable(product.getAvailable() - 1);
            productRepository.save(product);
        } else {
            productService.handleExpiredProduct(product);
        }
    }
}
