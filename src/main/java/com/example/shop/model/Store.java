package com.example.shop.model;

import com.example.shop.exceptions.MyResourceNotFoundException;
import lombok.*;
import javax.persistence.*;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Product, Integer> storage;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Product, Integer> availableProducts = storage;

    public void removeProduct(Product product, Integer quantity){
        Integer previousQuantity = availableProducts.get(product);
        if(previousQuantity==null||previousQuantity<quantity){
            throw new MyResourceNotFoundException("There is not enough product in the basket.");
        }
        availableProducts.put(product,previousQuantity-quantity);
    }
}
