package com.openclassrooms.chatopbackend.service;

import com.openclassrooms.chatopbackend.interfaces.MessageInterface;
import com.openclassrooms.chatopbackend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageService implements MessageInterface {

    @Autowired
    MessageRepository messageRepository;


    @Override
    public Map<String, String> create(com.openclassrooms.chatopbackend.model.Message message){

            messageRepository.save(message);
            Map<String, String> response = new HashMap<>();
            response.put("message","Message send with success");
            return response;

    }
}
