package org.example.product_management_system.controller;


import org.example.product_management_system.dtos.OrderDTO;
import org.example.product_management_system.model.Order;
import org.example.product_management_system.model.Product;
import org.example.product_management_system.model.User;
import org.example.product_management_system.service.OrderService;
import org.example.product_management_system.service.ProductService;
import org.example.product_management_system.service.UserService;
import org.example.product_management_system.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/order")
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, ProductService productService, UserService userService) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<Object> addOrder(@RequestBody OrderDTO orderDTO) {
        try{
            Optional<User> user = userService.getUser(orderDTO.getUserId());
            Product product = productService.getProduct(orderDTO.getProductId());


            double price = (double)product.getPrice() * orderDTO.getQuantity();

            Order order = new Order();
            order.setQuantity(orderDTO.getQuantity());
            order.setPrice(price);
            order.setUserId(orderDTO.getUserId());
            order.setProduct(product);

            Order newOrder = orderService.addOrder(order);
            int newQuantity = product.getQuantity() - orderDTO.getQuantity();
            productService.updateProduct(orderDTO.getProductId(), null, null, null, newQuantity);
            return ResponseHandler.response("Order added successfully", HttpStatus.CREATED, newOrder);
        } catch (IllegalStateException e) {
            return ResponseHandler.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getOrders(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderService.getOrders(pageable);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(path = "{orderId}")
    public ResponseEntity<Object> getOrder(@PathVariable("orderId") Long orderId) {
        try {
            Order order = orderService.getOrder(orderId);
            return ResponseHandler.response("Order retrieved successfully", HttpStatus.OK, order);
        } catch (IllegalStateException e) {
            return ResponseHandler.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "{orderId}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseHandler.response("Order deleted successfully", HttpStatus.OK, null);
        } catch (IllegalStateException e) {
            return ResponseHandler.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<Object> getUserOrders(@PathVariable("userId") String userId) {
        return ResponseHandler.response("Orders retrieved successfully", HttpStatus.OK, orderService.getUserOrders(userId));
    }
}
