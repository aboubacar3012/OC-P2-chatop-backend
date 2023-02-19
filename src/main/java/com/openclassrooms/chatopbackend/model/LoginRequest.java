package com.openclassrooms.chatopbackend.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginRequest {
    @NotNull @Email @Length(min = 5, max = 50)
    private String email;

    @NotNull @Length(min = 0, max = 10)
    private String password;
}
