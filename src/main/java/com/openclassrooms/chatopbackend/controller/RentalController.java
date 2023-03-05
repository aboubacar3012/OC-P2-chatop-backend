package com.openclassrooms.chatopbackend.controller;

import com.openclassrooms.chatopbackend.model.Rental;
import com.openclassrooms.chatopbackend.model.User;
import com.openclassrooms.chatopbackend.service.RentalService;
import com.openclassrooms.chatopbackend.service.StorageService;
import com.openclassrooms.chatopbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
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
       try{
           Map<String, List<Rental>> rentals = rentalService.getAll();
           return ResponseEntity.ok().body(rentals);
       }catch(Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        try {
            Rental rental = rentalService.getById(id);
            if(rental != null){
                return ResponseEntity.ok().body(rental);
            }else{
                Map<String, String> response = new HashMap<>();
                response.put("message","Rental with id:" + id + " not found");
                return ResponseEntity.ok().body(response);
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @RequestHeader HttpHeaders headers,
            @RequestParam String name,
            @RequestParam Integer surface,
            @RequestParam Integer price,
            @RequestParam(value = "picture") MultipartFile picture,
            @RequestParam String description) throws FileNotFoundException {

        try{
            Optional<User> connectedUser = userService.getMe(headers);
            if(connectedUser.isEmpty()){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

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

            Map<String, String> response = rentalService.create(rental);
            return ResponseEntity.ok().body(response);



        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


    @PutMapping(value="/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable("id") int id,
                                    @RequestParam String name,
                                    @RequestParam Integer surface,
                                    @RequestParam Integer price,
                                    @RequestParam String description){
       try{
           Rental rental = new Rental();
           rental.setName(name);
           rental.setSurface(surface);
           rental.setPrice(price);
           rental.setDescription(description);

           Map<String, String> response = rentalService.update(id, rental);
           return ResponseEntity.ok().body(response);
       }catch(Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
    }
}
