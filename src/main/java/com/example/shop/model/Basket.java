package com.example.shop.model;

import com.example.shop.exceptions.MyResourceNotFoundException;
import lombok.*;
import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "baskets")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Product, Integer> products;
    private Integer discount;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    public void addProduct(Product product, Integer extraQuantity) {
        if (products.containsKey(product)) {
            Integer curQantity = products.get(product);
            products.put(product, curQantity + extraQuantity);
        } else {
            products.put(product, extraQuantity);
        }
    }

    public void removeProduct(Product product, Integer quantity) {
        if (!products.containsKey(product)) {
            throw new MyResourceNotFoundException("The basket doesn't contain this product");
        }
        Integer previousQuantity = products.get(product);
        int curQuantity = previousQuantity - quantity;
        if (curQuantity < 0) {
            throw new MyResourceNotFoundException("The basket doesn't contain this quantity of the product");
        } else if (curQuantity == 0) {
            products.remove(product);
        } else {
            products.put(product, curQuantity);
        }
    }
}
