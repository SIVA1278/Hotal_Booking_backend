package com.hotelbooking.system.Service;

import com.hotelbooking.system.Entity.Hotel;
import com.hotelbooking.system.Entity.Rooms;
import com.hotelbooking.system.Repository.HotelRepository;
import com.hotelbooking.system.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public Rooms addRoom(Long hotelId, Rooms rooms){
        Hotel hotel=hotelRepository.findById(hotelId)
                .orElseThrow(()-> new RuntimeException("Hotel not found"));
        rooms.setHotel(hotel);
        return roomRepository.save(rooms);
    }

    public List<Rooms>getRoomsByHotel(Long hotelId){
        return roomRepository.findByHotelId(hotelId);   }

    public Rooms getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
}
public void deleteRooms(Long id){
    roomRepository.deleteById(id);}

    public Rooms createRoom(Long hotelId, Rooms rooms) {

        Hotel hotel=hotelRepository.findById(hotelId)
                .orElseThrow(()-> new RuntimeException("Hotel not found"));
        rooms.setHotel(hotel);
        return roomRepository.save(rooms);
    }
    public List<Rooms> getAllRooms() {
        return roomRepository.findAll();
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public void saveRoom(Rooms rooms) {
        roomRepository.save(rooms);
    }
}

