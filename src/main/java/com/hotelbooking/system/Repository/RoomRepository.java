package com.hotelbooking.system.Repository;

import com.hotelbooking.system.Entity.Hotel;
import com.hotelbooking.system.Entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository  extends JpaRepository<Rooms,Long> {
    List<Rooms> findByHotel(Hotel hotel);

    List<Rooms> findByHotelId(Long hotelId);
}
