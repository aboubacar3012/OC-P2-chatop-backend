package com.openclassrooms.chatopbackend.repository;

import com.openclassrooms.chatopbackend.model.message.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
}
