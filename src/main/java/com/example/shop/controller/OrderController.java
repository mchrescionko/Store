package com.example.shop.controller;

import com.example.shop.exceptions.MyResourceNotFoundException;
import com.example.shop.model.Order;
import com.example.shop.requests.CreateOrderRequest;
import com.example.shop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RequestMapping(path = "api/orders")
@RestController
public class OrderController {
    private OrderService orderService;

    @GetMapping()
    public ResponseEntity<List<Order>> getOrdersByUserSortBy(@RequestParam Map<String,String> allRequestParams) {
        List<Order> orders = orderService.getOrdersByUserSortBy(allRequestParams);
        return ResponseEntity.ok(orders);
    }

    @GetMapping()
    public ResponseEntity<Order> getOrder(@PathVariable Integer orderId) {
        Order order;
        try{
            order = orderService.getOrder(orderId);
        }catch(MyResourceNotFoundException e){
            return ResponseEntity.notFound().header("Reason", e.getMessage()).build();
        }
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        Order order;
        try{
            order = orderService.makeOrder(createOrderRequest.getBasketId());
        }catch (MyResourceNotFoundException e){
            return ResponseEntity.notFound().header("Reason", e.getMessage()).build();
        }
        return ResponseEntity.created(URI.create("api/orders/" + order.getId())).build();
    }
}