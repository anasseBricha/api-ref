package com.nimbleways.springboilerplate.services.strategy;

import org.springframework.stereotype.Component;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.services.implementations.ProductService;

@Component
public class NormalProductProcessingStrategy implements ProductProcessingStrategy {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public NormalProductProcessingStrategy(ProductService productService,
                                           ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public String getType() {
        return "NORMAL";
    }

    @Override
    public void process(Product product) {
        if (product.getAvailable() > 0) {
            product.setAvailable(product.getAvailable() - 1);
            productRepository.save(product);
        } else if (product.getLeadTime() > 0) {
            productService.notifyDelay(product.getLeadTime(), product);
        }
    }
}
