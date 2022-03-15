package com.example.shop.requests;

import com.example.shop.model.Product;
import com.example.shop.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateBasketRequest {
    Integer discount;
    Integer userId;

}
