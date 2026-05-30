package com.hotelbooking.system.Config;

import com.hotelbooking.system.Entity.Hotel;
import com.hotelbooking.system.Entity.Rooms;
import com.hotelbooking.system.Entity.User;
import com.hotelbooking.system.Repository.HotelRepository;
import com.hotelbooking.system.Repository.RoomRepository;
import com.hotelbooking.system.Repository.UserRepository;
import com.hotelbooking.system.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findByEmail("admin@luxe.com").isEmpty()) {
            User admin = new User();
            admin.setName("System Admin");
            admin.setEmail("admin@luxe.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }


        if (userRepository.findByEmail("user@luxe.com").isEmpty()) {
            User user = new User();
            user.setName("Sivasp");
            user.setEmail("user@sivaji.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole(Role.USER);
            userRepository.save(user);
        }


        if (hotelRepository.count() == 0) {
            Hotel hotel = new Hotel();
            hotel.setName("The Grand Majestic");
            hotel.setCity("London");
            hotel.setAddress("100 Royal Mile");
            hotel.setDescription("A historic luxury hotel in the heart of London.");
            hotel.setRating(5.0);
            Hotel savedHotel = hotelRepository.save(hotel);

            Rooms r1 = new Rooms();
            r1.setRoomNumber("S101");
            r1.setRoomType("Deluxe Suite");
            r1.setPrice(450.0);
            r1.setAvailable(true);
            r1.setHotel(savedHotel);
            roomRepository.save(r1);

            Rooms r2 = new Rooms();
            r2.setRoomNumber("E201");
            r2.setRoomType("Executive Room");
            r2.setPrice(300.0);
            r2.setAvailable(true);
            r2.setHotel(savedHotel);
            roomRepository.save(r2);
        }
    }
}
