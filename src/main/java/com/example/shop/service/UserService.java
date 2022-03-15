package com.example.shop.service;

import com.example.shop.exceptions.MyResourceNotFoundException;
import com.example.shop.model.User;
import com.example.shop.repository.UserRepository;
import com.example.shop.requests.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public User createUser(UserRequest userRequest){
        User user = User.builder()
                .firstName(userRequest.getFirstname())
                .lastName(userRequest.getLastname())
                .address(userRequest.getAddress())
                .build();
        userRepository.save(user);
        System.out.println("user:" + user);
        return user;
    }

    public User getUser(Integer userId){
        return userRepository.findById(userId).orElseThrow(()->new MyResourceNotFoundException("There isn't a user with such id"));
    }

}
