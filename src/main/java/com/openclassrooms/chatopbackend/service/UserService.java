package com.openclassrooms.chatopbackend.service;

import com.openclassrooms.chatopbackend.config.JwtTokenUtil;
import com.openclassrooms.chatopbackend.interfaces.UserInterface;
import com.openclassrooms.chatopbackend.model.User;
import com.openclassrooms.chatopbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserInterface {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     *
     * @param headers
     * @return Optional<User>
     */
    @Override
    public Optional<User> getMe(HttpHeaders headers){
        String token = headers.get("authorization").get(0).split(" ")[1].trim();
        String userEmail = jwtTokenUtil.getSubject(token).split(",")[1];
        User me = userRepository.findByEmail(userEmail).get();

        return Optional.of(me);
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public User register(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        return userRepository.save(user);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public User getUserById(int id){
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
}
