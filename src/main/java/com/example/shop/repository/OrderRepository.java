package com.example.shop.repository;

import com.example.shop.model.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserIdOrderByPrice(Integer userId);

    List<Order> findByUserId(Integer userId, Sort sort);

    List<Order> findByUserId(Integer userId);

}
