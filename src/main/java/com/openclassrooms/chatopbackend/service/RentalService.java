package com.openclassrooms.chatopbackend.service;

import com.openclassrooms.chatopbackend.model.Rental;
import com.openclassrooms.chatopbackend.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    RentalRepository rentalRepository;

    public ResponseEntity<?> getAll(){
        List<Rental> rentalsList = (List<Rental>) rentalRepository.findAll();
        Map<String, List<Rental>> rentals = new HashMap<>();
        rentals.put("rentals", rentalsList);
        return ResponseEntity.ok().body(rentals);

    }

    public Optional<Rental> getById(int id){
        return rentalRepository.findById(id);
    }

    public ResponseEntity<?> create(Rental rental){
        try{
            rentalRepository.save(rental);
            Map<String, String> response = new HashMap<>();
            response.put("message","Rental created !");
            return ResponseEntity.ok().body(response);
        }catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity<?> update(int id, Rental rental){
        try{
           Optional<Rental> oldRental = rentalRepository.findById(id);
           if(oldRental.isPresent()){
               Rental _rental = oldRental.get();
               if(rental.getName() != null){
                    _rental.setName(rental.getName());
               }
               if(rental.getDescription() != null){
                    _rental.setDescription(rental.getDescription());
               }
               if(rental.getPrice() != 0){
                    _rental.setPrice(rental.getPrice());
               }
               if(rental.getSurface() != 0){
                    _rental.setSurface(rental.getSurface());
               }
               if(rental.getOwner_id() != 0){
                    _rental.setOwner_id(rental.getOwner_id());
               }
//               if(rental.getPicture() != null){
//                    _rental.setPicture(rental.getPicture());
//               }
               _rental.setUpdated_at(LocalDate.now());

               rentalRepository.save(_rental);

               Map<String, String> response = new HashMap<>();
               response.put("message","Rental updated !");
               return ResponseEntity.ok().body(response);
           }else {
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }

        }catch (BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
