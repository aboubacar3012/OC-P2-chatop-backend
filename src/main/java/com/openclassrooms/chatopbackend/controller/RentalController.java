package com.openclassrooms.chatopbackend.controller;

import com.openclassrooms.chatopbackend.model.rental.Rental;
import com.openclassrooms.chatopbackend.model.user.User;
import com.openclassrooms.chatopbackend.service.RentalService;
import com.openclassrooms.chatopbackend.service.StorageService;
import com.openclassrooms.chatopbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    UserService userService;

    @Value("${image.base.url}")
    private String imageBaseUrl;

    @Autowired
    RentalService rentalService;

    @Autowired
    private StorageService storageService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return rentalService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Rental> getById(@PathVariable("id") int id){
        return rentalService.getById(id);
    }

//    @PostMapping(value = "")
//    public ResponseEntity<?> create(@RequestBody @Valid Rental rental){
//        return rentalService.create(rental);
//    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @RequestHeader HttpHeaders headers,
            @RequestParam String name,
            @RequestParam Integer surface,
            @RequestParam Integer price,
            @RequestParam(value = "picture") MultipartFile picture,
            @RequestParam String description) throws FileNotFoundException {

        Optional<User> connectedUser = userService.getMe(headers);

        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setOwner_id(connectedUser.get().getId());

        String storedImage = storageService.uploadFile(picture);
        if(storedImage != null){
            rental.setPicture(imageBaseUrl+storageService.uploadFile(picture));
        }else{
            Map<String, String> response = new HashMap<>();
            response.put("message","Image format is not valid");
            return ResponseEntity.badRequest().body(response);
        }

        return rentalService.create(rental);

    }


    @PutMapping(value="/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable("id") int id,
                                    @RequestParam String name,
                                    @RequestParam Integer surface,
                                    @RequestParam Integer price,
                                    @RequestParam String description){
        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);

        return rentalService.update(id, rental);
    }
}
