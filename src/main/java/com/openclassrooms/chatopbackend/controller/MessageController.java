package com.openclassrooms.chatopbackend.controller;

import com.openclassrooms.chatopbackend.model.Message;
import com.openclassrooms.chatopbackend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    MessageService messageService;
    @PostMapping("")
    public ResponseEntity<?> createMessage(@RequestBody Message message){
        try{
            Map<String, String> response =  messageService.create(message);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
