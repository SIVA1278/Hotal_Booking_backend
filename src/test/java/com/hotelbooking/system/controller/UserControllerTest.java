package com.hotelbooking.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.system.controller.UserController;
import com.hotelbooking.system.Entity.User;
import com.hotelbooking.system.Service.UserService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)

public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllUsers() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setName("Siva");

        when(userService.getAllUsers())
                .thenReturn(List.of(user));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Siva"));
    }

    @Test
    void testCreateUser() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setName("Siva");

        when(userService.registerUser(user))
                .thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Siva"));
    }
}