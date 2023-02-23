package com.openclassrooms.chatopbackend.service;

import com.openclassrooms.chatopbackend.config.JwtTokenUtil;
import com.openclassrooms.chatopbackend.model.User;
import com.openclassrooms.chatopbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public Optional<User> getMe(HttpHeaders headers){
        String token = headers.get("authorization").get(0).split(" ")[1].trim();
        String userEmail = jwtTokenUtil.getSubject(token).split(",")[1];
        User me = userRepository.findByEmail(userEmail).get();

        return Optional.of(me);
    }

    public User register(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public ResponseEntity<?> getUserById(int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return ResponseEntity.ok().body(user.get());
        }
        Map<String, String> response = new HashMap<>();
        response.put("message","User with id:" + id + " not found");
        return ResponseEntity.ok().body(response);
    }
}
