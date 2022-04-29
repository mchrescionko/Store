package com.example.shop.controller;

import com.example.shop.model.Store;
import com.example.shop.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "api/store")
@AllArgsConstructor
public class StoreController {
    private StoreService storeService;

    @GetMapping
    ResponseEntity<Store> getStore() {
        if (storeService.doesStoreExist()) {
            Store store = storeService.getStore();
            return ResponseEntity.created(URI.create("api/store")).body(store);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    ResponseEntity<Store> createStorage() {
        if (!storeService.doesStoreExist()) {
            Store store = storeService.createStorage();
            return ResponseEntity.created(URI.create("api/store")).body(store);
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
