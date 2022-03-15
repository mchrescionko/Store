package com.example.shop.service;

import com.example.shop.exceptions.MyResourceNotFoundException;
import com.example.shop.model.Product;
import com.example.shop.model.Store;
import com.example.shop.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class StoreService {
    private StoreRepository storeRepository;
    private ProductService productService;

    public Store getStore(){
        if(doesStoreExist()){
            return storeRepository.findAll().get(0);
        }
        throw new MyResourceNotFoundException("Store doesn't exist (storage is empty)!");
    }

    public Store createStorage(){
        Product product1 = productService.getProductById(1);
        Product product2 = productService.getProductById(2);
        Map<Product, Integer> products = new HashMap<>();
        products.put(product1, 5);
        products.put(product2, 5);
        Store store = Store.builder()
                .availableProducts(products)
                .storage(products)
                .build();
        storeRepository.save(store);
        return store;
    }

    public boolean doesStoreExist(){
        return !storeRepository.findAll().isEmpty();
    }

    public void addProducts(Product product, Integer quantity){
        Store store = getStore();
        if(store.getStorage().containsKey(product)){
            int previousQuantity = store.getStorage().get(product);
            store.getStorage().put(product, previousQuantity+quantity);
        }else{
            store.getStorage().put(product,quantity);
        }
    }
}
