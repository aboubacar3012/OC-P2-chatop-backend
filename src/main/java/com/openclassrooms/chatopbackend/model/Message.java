package com.openclassrooms.chatopbackend.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int rental_id;

    private int user_id;

    private String message;

    private LocalDate created_at = LocalDate.now();

    private LocalDate updated_at = LocalDate.now();
}
