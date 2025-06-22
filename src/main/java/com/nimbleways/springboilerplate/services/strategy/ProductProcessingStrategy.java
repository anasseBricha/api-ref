package com.nimbleways.springboilerplate.services.strategy;

import com.nimbleways.springboilerplate.entities.Product;

public interface ProductProcessingStrategy {
    String getType();
    void process(Product product);
}
