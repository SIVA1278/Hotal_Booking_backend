package com.hotelbooking.system.Service;

import com.hotelbooking.system.Entity.Bookings;
import com.hotelbooking.system.Entity.Hotel;
import com.hotelbooking.system.Entity.Rooms;
import com.hotelbooking.system.Repository.BookingRepository;
import com.hotelbooking.system.Repository.HotelRepository;
import com.hotelbooking.system.Repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public List<Hotel> searchHotelsByCity(String city){
        return hotelRepository.findByCityIgnoreCase(city);
    }
    public List<Rooms> getAvailableRooms(Long hotelId ,LocalDate checkIn, LocalDate checkOut){
        List<Rooms> allRooms = roomRepository.findByHotelId(hotelId);
        List<Rooms> availableRooms = new ArrayList<>();
        for (Rooms room : allRooms) {
            List<Bookings> conflicts =
                    bookingRepository.findConflictingBookings(
                            room.getId(), checkIn, checkOut
                    );
            if (conflicts.isEmpty()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

}
