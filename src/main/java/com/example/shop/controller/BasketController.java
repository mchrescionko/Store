package com.example.shop.controller;

import com.example.shop.exceptions.MyResourceNotFoundException;
import com.example.shop.model.Basket;
import com.example.shop.requests.*;
import com.example.shop.service.BasketService;
import com.example.shop.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "api/baskets")
@AllArgsConstructor
public class BasketController {
    private BasketService basketService;
    private StoreService storeService;

    @PostMapping
    public ResponseEntity<Basket> createBasket(@RequestBody CreateBasketRequest createBasketRequest) {
        Basket basket = basketService.create(createBasketRequest);
        return ResponseEntity.created(URI.create("api/baskets/" + basket.getId())).body(basket);
    }

    @PatchMapping("/{basketId}/products")
    public ResponseEntity<Basket> addProductToBasket(@RequestBody AddProductToBasketRequest addProductToBasketRequest, @PathVariable Integer basketId) {
        Basket basket;
        try {
            basket = basketService.addProductToBasket(addProductToBasketRequest, basketId);
        } catch (MyResourceNotFoundException e) {
            return ResponseEntity.notFound().header("reason", e.getMessage()).build();
        }
        return ResponseEntity.ok().body(basket);
    }

    @GetMapping("/{basketId}")
    public ResponseEntity<Basket> getBasket(@PathVariable Integer basketId) {
        Basket basket;
        try {
            basket = basketService.getBasket(basketId);
        } catch (MyResourceNotFoundException e) {
            return ResponseEntity.notFound().header("Reason", e.getMessage()).build();
        }
        return ResponseEntity.ok().body(basket);

    }

    @DeleteMapping("/{basketId}")
    public ResponseEntity<String> removeBasket(@PathVariable Integer basketId) {
        try {
            basketService.removeBasket(basketId);
        } catch (MyResourceNotFoundException e) {
            return ResponseEntity.notFound().header("Reason", e.getMessage()).build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{basketId}/products/{productId}")
    public ResponseEntity<Basket> removeProductFromBasket(@RequestBody RemoveProductFromBasketRequest removeProductFromBasketRequest, @PathVariable Integer basketId, @PathVariable Integer productId) {
        Basket basket;
        try {
            basket = basketService.removeProductFromBasket(removeProductFromBasketRequest, basketId, productId);
        } catch (MyResourceNotFoundException e) {
            return ResponseEntity.notFound().header("Reason", e.getMessage()).build();
        }
        return ResponseEntity.ok(basket);
    }

    @PatchMapping("/{basketId}")
    public ResponseEntity<Basket> uploadBasket(@RequestBody UploadBasketRequest uploadBasketRequest, @PathVariable Integer basketId) {
        Basket basket;
        try {
            basket = basketService.upload(uploadBasketRequest, basketId);
        } catch (MyResourceNotFoundException e) {
            return ResponseEntity.notFound().header("Reason", e.getMessage()).build();
        }
        return ResponseEntity.ok(basket);
    }
}
