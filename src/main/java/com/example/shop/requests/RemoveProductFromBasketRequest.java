package com.example.shop.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RemoveProductFromBasketRequest {
    Integer productId;
    Integer userId;
    Integer quantity;
}
