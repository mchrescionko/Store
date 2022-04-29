package com.example.shop.service;

import com.example.shop.exceptions.MyResourceNotFoundException;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).orElseThrow(() -> new MyResourceNotFoundException("The product with such id doesn't exist!"));
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
