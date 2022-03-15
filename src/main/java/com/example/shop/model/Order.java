package com.example.shop.model;

import lombok.*;
import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "orders")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Product, Integer> products;
    private Integer discount;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    private Integer price;

    public void applyDiscount(){
        price = 0;
        for (Map.Entry<Product, Integer> productIntegerEntry : products.entrySet()) {
            price = price + productIntegerEntry.getKey().getPrice() * productIntegerEntry.getValue() * discount;
        }
    }
}