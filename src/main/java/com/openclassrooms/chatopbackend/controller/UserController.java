package com.openclassrooms.chatopbackend.controller;

import com.openclassrooms.chatopbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") int id){
        return userService.getUserById(id);
    }
}