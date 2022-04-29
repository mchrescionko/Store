package com.example.shop.controller;

import com.example.shop.exceptions.MyResourceNotFoundException;
import com.example.shop.model.Product;
import com.example.shop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/products")
public class ProductController {
    private ProductService productService;

    @GetMapping
    ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok().body(productService.getProducts());
    }

    @GetMapping("/{productId}")
    ResponseEntity<Product> getProductById(@PathVariable Integer productId) {
        Product product;
        try {
            product = productService.getProductById(productId);
        } catch (MyResourceNotFoundException e) {
            return ResponseEntity.notFound().header("Reason", e.getMessage()).build();
        }
        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        System.out.println("product: " + product);
        productService.saveProduct(product);
        return ResponseEntity.created(URI.create("api/products/" + product.getId()))
                .body(product);
    }
}
