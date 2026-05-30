package com.hotelbooking.system.Repository;

import com.hotelbooking.system.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
    List<Hotel> findByCityIgnoreCase(String city);
    List<Hotel> findByCity(String city);
}
