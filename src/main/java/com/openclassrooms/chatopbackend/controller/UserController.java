package com.openclassrooms.chatopbackend.controller;

import com.openclassrooms.chatopbackend.model.User;
import com.openclassrooms.chatopbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") int id){
        try{
            User user = userService.getUserById(id);
            if(user != null){
                return ResponseEntity.ok().body(user);
            }else{
                Map<String, String> response = new HashMap<>();
                response.put("message","User with id:" + id + " not found");
                return ResponseEntity.ok().body(response);
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}