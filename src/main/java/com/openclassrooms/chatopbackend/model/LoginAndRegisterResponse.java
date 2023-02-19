package com.openclassrooms.chatopbackend.model;

import lombok.Data;

@Data
public class LoginAndRegisterResponse {
    private String token;

    public LoginAndRegisterResponse(String token){
        this.token = token;
    }
}
