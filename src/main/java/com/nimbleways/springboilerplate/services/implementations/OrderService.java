package com.nimbleways.springboilerplate.services.implementations;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimbleways.springboilerplate.dto.product.ProcessOrderResponse;
import com.nimbleways.springboilerplate.entities.Order;
import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.repositories.OrderRepository;
import com.nimbleways.springboilerplate.services.strategy.ProductProcessingStrategy;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final Map<String, ProductProcessingStrategy> strategyMap;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        List<ProductProcessingStrategy> strategies) {
        this.orderRepository = orderRepository;
        this.strategyMap = strategies.stream()
                                     .collect(Collectors.toMap(ProductProcessingStrategy::getType, s -> s));
    }

    public ProcessOrderResponse processOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        for (Product product : order.getItems()) {
            ProductProcessingStrategy strategy = strategyMap.get(product.getType());
            if (strategy != null) {
                strategy.process(product);
            }
        }
        return new ProcessOrderResponse(order.getId());
    }
}
