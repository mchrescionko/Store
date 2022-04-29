package com.example.shop.controller;

import com.example.shop.exceptions.MyResourceNotFoundException;
import com.example.shop.model.User;
import com.example.shop.requests.UserRequest;
import com.example.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping(path="api/users")
public class UserController {
    private UserService userService;

    @PostMapping
    ResponseEntity<User> createUser(@RequestBody UserRequest userRequest){
        User user;
        try{
            user = userService.createUser(userRequest);
        }catch(MyResourceNotFoundException e){
            return ResponseEntity.notFound().header("Resaon", e.getMessage()).build();
        }
        return ResponseEntity.created(URI.create("api/users/"+user.getId())).build();
    }

    @GetMapping
    ResponseEntity<User> getUser(@PathVariable Integer userId){
        User user;
        try{
            user = userService.getUser(userId);
        }catch(MyResourceNotFoundException e){
            return ResponseEntity.notFound().header("Resaon", e.getMessage()).build();
        }
        return ResponseEntity.ok().body(user);
    }

    //pelny crud
}

