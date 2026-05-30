package com.hotelbooking.system.controller;

import com.hotelbooking.system.Entity.User;
import com.hotelbooking.system.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(
            @RequestBody User user) {

        return userService.registerUser(user);
    }
}