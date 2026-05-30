package com.hotelbooking.system.service;

import com.hotelbooking.system.Entity.Hotel;
import com.hotelbooking.system.Repository.HotelRepository;
import com.hotelbooking.system.Service.HotelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @Test
    void testCreateHotel() {

        Hotel hotel = new Hotel();

        hotel.setName("Siva Hotel");

        hotelService.saveHotel(hotel);

        verify(hotelRepository ,times(1)).save(hotel);


    }
}