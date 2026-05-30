package com.hotelbooking.system.Service;

import com.hotelbooking.system.Entity.Hotel;
import com.hotelbooking.system.Repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public Hotel crearteHotel(Hotel hotel)
    {
        return hotelRepository.save(hotel);
    }

    public List<Hotel> getHotels(){
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id){
        return hotelRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Hotel not found"));
    }

    public Hotel updateHotel(Long id,Hotel updateHotel ){

        Hotel hotel =hotelRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Hotel not found"));
        hotel.setName(updateHotel.getName());
        hotel.setCity(updateHotel.getCity());
        hotel.setAddress(updateHotel.getAddress());
        hotel.setDescription(updateHotel.getDescription());
        hotel.setRating(updateHotel.getRating());
        return hotelRepository.save(hotel);

    }

    public void deleteHotel(Long id){

        hotelRepository.deleteById(id);
    }

    public List<Hotel> searchByCity(String city){
        return hotelRepository.findByCity(city);
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel createHotel(Hotel hotel) {

        return hotelRepository.save(hotel);
    }

    public void saveHotel(Hotel hotel) {
         hotelRepository.save(hotel);
    }
}


//old correct
