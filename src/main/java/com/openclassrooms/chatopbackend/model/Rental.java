package com.openclassrooms.chatopbackend.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private int surface;

    private int price;

    private String picture;

    private String description;

    private int owner_id;

    private LocalDate created_at = LocalDate.now();

    private LocalDate updated_at = LocalDate.now();
}
