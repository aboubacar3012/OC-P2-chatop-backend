package com.openclassrooms.chatopbackend.interfaces;

import com.openclassrooms.chatopbackend.model.User;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

public interface UserInterface {
    public User getUserById(int id);
    public User register(User user);
    public Optional<User> getMe(HttpHeaders headers);
}
