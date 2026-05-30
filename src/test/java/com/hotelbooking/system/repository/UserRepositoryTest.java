package com.hotelbooking.system.repository;

import com.hotelbooking.system.Entity.User;
import com.hotelbooking.system.Repository.UserRepository;
import com.hotelbooking.system.Service.UserService;
import com.hotelbooking.system.controller.BookingControllerTest;
import com.hotelbooking.system.enums.Role;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testSaveUser() {

        User user = new User();

        user.setName("Siva");
        user.setEmail("siva123@gmail.com");
        user.setPassword("12345");
        user.setRole(Role.USER);
        User savedUser =
                userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("Siva",
                savedUser.getName());
    }

    @Test
    void testGetAllUsers() {

        User user = new User();
        user.setName("Arun");
        user.setEmail("arun@gmail.com");
        user.setPassword("12345");
        user.setRole(Role.USER);
        userRepository.save(user);
        List<User> users =
                userRepository.findAll();

        assertFalse(users.isEmpty());
    }
}