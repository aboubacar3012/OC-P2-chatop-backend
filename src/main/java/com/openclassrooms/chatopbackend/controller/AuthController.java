package com.openclassrooms.chatopbackend.controller;

import javax.validation.Valid;

import com.openclassrooms.chatopbackend.config.JwtTokenUtil;
import com.openclassrooms.chatopbackend.model.LoginRequest;
import com.openclassrooms.chatopbackend.model.LoginAndRegisterResponse;
import com.openclassrooms.chatopbackend.model.User;
import com.openclassrooms.chatopbackend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api("Api pour l'authentification")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    public UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @ApiOperation(value = "permet de connecter un utilisateur grace a son email et mot de passe")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            String token = jwtTokenUtil.generateAccessToken(user);
            LoginAndRegisterResponse response = new LoginAndRegisterResponse(token);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/me")
    public Optional<User> getMe(@RequestHeader HttpHeaders headers){
        return userService.getMe(headers);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user){
        try{
            User savedUser = userService.register(user);
            String token = jwtTokenUtil.generateAccessToken(user);
            LoginAndRegisterResponse response = new LoginAndRegisterResponse(token);
            return ResponseEntity.ok().body(response);

        }catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
