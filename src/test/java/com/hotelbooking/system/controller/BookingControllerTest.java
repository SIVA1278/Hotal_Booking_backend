package com.hotelbooking.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.system.Entity.Bookings;
import com.hotelbooking.system.Repository.RoomRepository;
import com.hotelbooking.system.Repository.UserRepository;
import com.hotelbooking.system.Service.BookingService;

import com.hotelbooking.system.Service.EmailService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private RoomRepository roomRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @org.springframework.security.test.context.support.WithMockUser(username = "test@test.com")
    void testGetAllBookings() throws Exception {
        
        com.hotelbooking.system.Entity.User user = new com.hotelbooking.system.Entity.User();
        user.setId(1L);
        when(userRepository.findByEmail("test@test.com")).thenReturn(java.util.Optional.of(user));

        org.springframework.security.core.Authentication mockAuth = org.mockito.Mockito.mock(org.springframework.security.core.Authentication.class);
        when(mockAuth.getName()).thenReturn("test@test.com");

        mockMvc.perform(get("/user/my-bookings").principal(mockAuth))
                .andExpect(status().isOk()); // View my-bookings should return 200 OK
    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser(username = "test@test.com")
    void testCreateBooking() throws Exception {

        com.hotelbooking.system.Entity.Hotel hotel = new com.hotelbooking.system.Entity.Hotel();
        hotel.setId(1L);

        com.hotelbooking.system.Entity.Rooms room = new com.hotelbooking.system.Entity.Rooms();
        room.setId(1L);
        room.setHotel(hotel);
        when(roomRepository.findById(1L)).thenReturn(java.util.Optional.of(room));
        
        com.hotelbooking.system.Entity.User user = new com.hotelbooking.system.Entity.User();
        user.setId(1L);
        when(userRepository.findByEmail("test@test.com")).thenReturn(java.util.Optional.of(user));

        when(bookingService.isRoomAvailable(any(), any(), any())).thenReturn(true);
        when(bookingService.saveBooking(any())).thenReturn(new Bookings());

        org.springframework.security.core.Authentication mockAuth = org.mockito.Mockito.mock(org.springframework.security.core.Authentication.class);
        when(mockAuth.getName()).thenReturn("test@test.com");

        mockMvc.perform(post("/user/save-booking")
                        .principal(mockAuth)
                        .param("roomId", "1")
                        .param("checkInDate", "2026-06-10")
                        .param("checkOutDate", "2026-06-15"))
                .andExpect(status().is3xxRedirection()); // Expect redirect to payment
    }
}