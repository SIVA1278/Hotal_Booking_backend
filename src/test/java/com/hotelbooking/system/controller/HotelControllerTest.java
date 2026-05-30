package com.hotelbooking.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.system.Entity.Hotel;
import com.hotelbooking.system.Service.HotelService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllHotels() throws Exception {

        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Taj Hotel");

        when(hotelService.getAllHotels())
                .thenReturn(List.of(hotel));

        mockMvc.perform(get("/hotels"))
                .andExpect(status().isOk())
                .andExpect(view().name("hotel-list"))
                .andExpect(model().attributeExists("hotels"));
    }

    @Test
    void testSaveHotel() throws Exception {

        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Taj Hotel");

        mockMvc.perform(post("/admin/save-hotel")
                        .flashAttr("hotel", hotel))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hotels"));
    }
}