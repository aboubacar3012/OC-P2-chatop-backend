package com.openclassrooms.chatopbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class OcP2ChatopBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcP2ChatopBackendApplication.class, args);
    }

}
