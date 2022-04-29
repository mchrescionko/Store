package com.example.shop.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "products")
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price;
    private String picture;
    private String details;
    @ElementCollection
    private Map<String, String> parameters;
    private Double rating;

    public Product() {
    }

    public Product(Integer id, String name, Integer price, String picture, String details, Map<String, String> parameters, Double rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.details = details;
        this.parameters = parameters;
        this.rating = rating;
    }
}
