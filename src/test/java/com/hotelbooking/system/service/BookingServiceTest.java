package com.hotelbooking.system.service;

import com.hotelbooking.system.DTO.BookingRequest;
import com.hotelbooking.system.Entity.Bookings;
import com.hotelbooking.system.Entity.Rooms;
import com.hotelbooking.system.Entity.User;
import com.hotelbooking.system.Repository.BookingRepository;
import com.hotelbooking.system.Repository.RoomRepository;
import com.hotelbooking.system.Repository.UserRepository;
import com.hotelbooking.system.Service.BookingService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void testCreateBooking() {

        User user = new User();
        user.setId(1L);

        Rooms room = new Rooms();
        room.setId(1L);

        BookingRequest request = new BookingRequest();

        request.setUserId(1L);
        request.setRoomId(1L);

        request.setCheckInDate(LocalDate.now());

        request.setCheckOutDate(
                LocalDate.now().plusDays(2)
        );

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(roomRepository.findById(1L))
                .thenReturn(Optional.of(room));

        when(bookingRepository.save(any(Bookings.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Bookings booking =
                bookingService.createBooking(request);

        assertNotNull(booking);

        verify(bookingRepository, times(1))
                .save(any(Bookings.class));
    }
}