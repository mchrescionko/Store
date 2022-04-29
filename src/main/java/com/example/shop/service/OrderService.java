package com.example.shop.service;

import com.example.shop.exceptions.MyResourceNotFoundException;
import com.example.shop.model.Basket;
import com.example.shop.model.Order;
import com.example.shop.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private BasketService basketService;

    public List<Order> getOrdersByUserSortBy(Map<String, String> allRequestParams) {
        if (allRequestParams.containsKey("userId") && allRequestParams.containsKey("sortBy")) {
            return orderRepository.findByUserId(Integer.parseInt(allRequestParams.get("userId")), Sort.by(Sort.Direction.ASC, allRequestParams.get("sortBy")));
        } else if (allRequestParams.containsKey("userId")) {
            return orderRepository.findByUserId(Integer.parseInt(allRequestParams.get("userId")));
        } else if (allRequestParams.containsKey("sortBy")) {
            return orderRepository.findAll(Sort.by(Sort.Direction.ASC, allRequestParams.get("sortBy")));
        } else {
            return orderRepository.findAll();
        }
    }

    public Order makeOrder(Integer basketId) {
        Basket basket = basketService.getBasket(basketId);
        Order order = Order.builder()
                .discount(basket.getDiscount())
                .products(basket.getProducts())
                .user(basket.getUser())
                .build();
        order.applyDiscount();
        basketService.deleteBasket(basketId);
        orderRepository.save(order);
        return order;
    }

    public Order getOrder(Integer orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new MyResourceNotFoundException("There isn't a user with such id"));
    }
}
