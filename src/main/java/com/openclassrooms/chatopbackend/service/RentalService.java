package com.openclassrooms.chatopbackend.service;

import com.openclassrooms.chatopbackend.interfaces.RentalInterface;
import com.openclassrooms.chatopbackend.model.Rental;
import com.openclassrooms.chatopbackend.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RentalService implements RentalInterface {

    @Autowired
    RentalRepository rentalRepository;

    @Override
    public  Map<String, List<com.openclassrooms.chatopbackend.model.Rental>> getAll(){
        List<com.openclassrooms.chatopbackend.model.Rental> rentalsList = (List<com.openclassrooms.chatopbackend.model.Rental>) rentalRepository.findAll();
        Map<String, List<com.openclassrooms.chatopbackend.model.Rental>> rentals = new HashMap<>();
        rentals.put("rentals", rentalsList);
        return rentals;

    }

    @Override
    public Rental getById(int id){
        Optional<Rental> rental = rentalRepository.findById(id);
        return rental.orElse(null);
    }

    @Override
    public Map<String, String> create(Rental rental){
        rentalRepository.save(rental);
        Map<String, String> response = new HashMap<>();
        response.put("message","Rental created !");
       return response;
    }

    @Override
    public Map<String, String> update(int id, Rental rental){
        Map<String, String> response = new HashMap<>();

        Optional<Rental> oldRental = rentalRepository.findById(id);
        if (oldRental.isPresent()) {
            Rental _rental = oldRental.get();
            if (rental.getName() != null) {
                _rental.setName(rental.getName());
            }
            if (rental.getDescription() != null) {
                _rental.setDescription(rental.getDescription());
            }
            if (rental.getPrice() != 0) {
                _rental.setPrice(rental.getPrice());
            }
            if (rental.getSurface() != 0) {
                _rental.setSurface(rental.getSurface());
            }
            if (rental.getOwner_id() != 0) {
                _rental.setOwner_id(rental.getOwner_id());
            }
            _rental.setUpdated_at(LocalDate.now());

            rentalRepository.save(_rental);

            response.put("message", "Rental updated !");
            return response;
        } else {
            response.put("message", "Rental is not found !");
            return response;
        }

    }

}
