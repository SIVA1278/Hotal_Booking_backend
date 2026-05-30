package com.hotelbooking.system.repository;

import com.hotelbooking.system.Entity.Hotel;
import com.hotelbooking.system.Repository.HotelRepository;

import com.hotelbooking.system.controller.BookingControllerTest;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void testSaveHotel() {
        Hotel hotel = new Hotel();
        hotel.setName("Raj Hotel");
        hotel.setCity("Chennai");
        hotel.setAddress("Anna Nagar");
        hotel.setDescription("Luxury Hotel");
        hotel.setRating(4.0);
        Hotel savedHotel = hotelRepository.save(hotel);
        assertNotNull(savedHotel.getId());

        assertEquals("Raj Hotel",
                savedHotel.getName());
    }

    @Test
    void testGetAllHotels() {

        Hotel hotel = new Hotel();

        hotel.setName("OYOs");
        hotel.setCity("Madurai");
        hotel.setAddress("Main Road");
        hotel.setDescription("Budget Hotel");
        hotel.setRating(4.0);
        hotelRepository.save(hotel);
        List<Hotel> hotels = hotelRepository.findAll();

        assertFalse(hotels.isEmpty());

    }
}