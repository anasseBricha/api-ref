package com.nimbleways.springboilerplate.services.strategy;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.services.implementations.ProductService;

@Component
public class SeasonalProductProcessingStrategy implements ProductProcessingStrategy {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public SeasonalProductProcessingStrategy(ProductService productService,
                                             ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public String getType() {
        return "SEASONAL";
    }

    @Override
    public void process(Product product) {
        LocalDate now = LocalDate.now();
        if (now.isAfter(product.getSeasonStartDate()) &&
            now.isBefore(product.getSeasonEndDate()) &&
            product.getAvailable() > 0) {
            product.setAvailable(product.getAvailable() - 1);
            productRepository.save(product);
        } else {
            productService.handleSeasonalProduct(product);
        }
    }
}
