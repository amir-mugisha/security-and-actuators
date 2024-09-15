package org.example.product_management_system.service;

import org.example.product_management_system.model.Order;
import org.example.product_management_system.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Order with id " + id + " not found"));

        orderRepository.deleteById(id);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(()
                ->new IllegalStateException("Order with id " + id + " not found"));
    }

    public List<Order> getUserOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }


    public Page<Order> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}
