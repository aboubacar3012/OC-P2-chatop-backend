package com.openclassrooms.chatopbackend.repository;

import com.openclassrooms.chatopbackend.model.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Integer> {

}
