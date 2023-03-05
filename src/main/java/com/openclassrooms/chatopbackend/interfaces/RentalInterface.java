package com.openclassrooms.chatopbackend.interfaces;

import java.util.List;
import java.util.Map;

public interface RentalInterface {
    public Map<String, List<com.openclassrooms.chatopbackend.model.Rental>> getAll();
    public com.openclassrooms.chatopbackend.model.Rental getById(int id);
    public Map<String, String> create(com.openclassrooms.chatopbackend.model.Rental rental);
    public Map<String, String> update(int id, com.openclassrooms.chatopbackend.model.Rental rental);
}
