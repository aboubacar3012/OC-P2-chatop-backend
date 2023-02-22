package com.openclassrooms.chatopbackend.service;

import com.openclassrooms.chatopbackend.model.message.Message;
import com.openclassrooms.chatopbackend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;


    public ResponseEntity<?> create(Message message){
        try{
            messageRepository.save(message);
            Map<String, String> response = new HashMap<>();
            response.put("message","Message send with success");
            return ResponseEntity.ok().body(response);
        }catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
