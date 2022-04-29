package com.example.shop.service;

import com.example.shop.exceptions.MyResourceNotFoundException;
import com.example.shop.model.*;
import com.example.shop.repository.BasketRepository;
import com.example.shop.requests.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class BasketService {
    private BasketRepository basketRepository;
    private ProductService productService;
    private UserService userService;
    private StoreService storeService;

    public Basket create(CreateBasketRequest createBasketRequest) {
        User user = userService.getUser(createBasketRequest.getUserId());
        Map<Product, Integer> products = new HashMap<>();
        Basket basket = Basket.builder()
                .products(products)
                .user(user)
                .discount(createBasketRequest.getDiscount())
                .build();
        basketRepository.save(basket);
        return basket;
    }

    public void removeBasket(Integer basketId) {
        if (basketRepository.existsById(basketId)) {
            basketRepository.deleteById(basketId);
        } else {
            throw new MyResourceNotFoundException("The basket with such id doesn't exist");
        }
    }

    public Basket addProductToBasket(AddProductToBasketRequest addProductToBasketRequest, Integer basketId) {
        Store store = storeService.getStore();
        Product product = productService.getProductById(addProductToBasketRequest.getProductId());
        store.removeProduct(product, addProductToBasketRequest.getQuantity());
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new MyResourceNotFoundException("The basket with such id doesn't exist!"));
        basket.addProduct(product, addProductToBasketRequest.getQuantity());
        basketRepository.save(basket);
        return basket;
    }

    public Basket removeProductFromBasket(RemoveProductFromBasketRequest removeProductFromBasketRequest, Integer basketId, Integer productId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new MyResourceNotFoundException("The basket with such id doesn't exist!"));
        Product product = productService.getProductById(productId);
        basket.removeProduct(product, removeProductFromBasketRequest.getQuantity());
        storeService.addProducts(product, removeProductFromBasketRequest.getQuantity());
        basketRepository.save(basket);
        return basket;
    }

    public Basket getBasket(Integer basketId) {
        return basketRepository.findById(basketId).orElseThrow(() -> new MyResourceNotFoundException("There isn't a basket with such id"));
    }

    public Basket upload(UploadBasketRequest uploadBasketRequest, Integer basketId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new MyResourceNotFoundException("There isn't a basket with such id"));
        if (uploadBasketRequest.getDiscount() != null) {
            basket.setDiscount(uploadBasketRequest.getDiscount());
        }
        if (uploadBasketRequest.getUserId() != null) {
            basket.setUser(userService.getUser(uploadBasketRequest.getUserId()));
        }
        basketRepository.save(basket);
        return basket;
    }

    public void deleteBasket(Integer basketId) {
        basketRepository.deleteById(basketId);
    }
}
