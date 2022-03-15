package com.example.shop.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SetDiscountRequest {
    Integer userId;
    Integer discount;
}
