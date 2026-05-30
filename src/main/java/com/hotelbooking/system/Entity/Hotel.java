package com.hotelbooking.system.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String address;
    private String description;
    private double rating;

    @jakarta.persistence.OneToMany(mappedBy = "hotel", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Rooms> rooms;
}
