package com.hotelbooking.system.repository;

import com.hotelbooking.system.Entity.Bookings;
import com.hotelbooking.system.Entity.Hotel;
import com.hotelbooking.system.Entity.Rooms;
import com.hotelbooking.system.Entity.User;
import com.hotelbooking.system.Repository.BookingRepository;
import com.hotelbooking.system.Repository.HotelRepository;
import com.hotelbooking.system.Repository.RoomRepository;
import com.hotelbooking.system.Repository.UserRepository;
import com.hotelbooking.system.controller.BookingControllerTest;
import com.hotelbooking.system.enums.BookingStatus;
import com.hotelbooking.system.enums.HotelType;
import com.hotelbooking.system.enums.Role;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void testSaveBooking() {

        User user = new User();
        user.setName("Siva");
        user.setEmail("siva@gmail.com");
        user.setPassword("12345");
        user.setRole(Role.USER);
        User savedUser =
                userRepository.save(user);
        Hotel hotel = new Hotel();

        hotel.setName("Raj");
        hotel.setCity("Chennai");
        hotel.setAddress("Anna Nagar");
        hotel.setDescription("Luxury");

        hotel.setRating(4.0);

        Hotel savedHotel =
                hotelRepository.save(hotel);
        Rooms room = new Rooms();
        room.setRoomNumber("10");
        room.setRoomType("SINGLE");
        room.setPrice(5000.0);
        room.setAvailable(true);
        room.setHotel(savedHotel);
        Rooms savedRoom =
                roomRepository.save(room);
        Bookings booking = new Bookings();
        booking.setUser(savedUser);
        booking.setRoom(savedRoom);
        booking.setCheckInDate(LocalDate.now());
        booking.setCheckOutDate(
                LocalDate.now().plusDays(2)
        );

        booking.setStatus(BookingStatus.CONFIRMED);
        Bookings savedBooking =
                bookingRepository.save(booking);

        assertNotNull(savedBooking.getId());
    }

    @Test
    void testGetAllBookings() {

        List<Bookings> bookings =
                bookingRepository.findAll();

        assertNotNull(bookings);
    }
}