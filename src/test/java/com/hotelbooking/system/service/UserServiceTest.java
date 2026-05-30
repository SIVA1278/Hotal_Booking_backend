package com.hotelbooking.system.service;

import com.hotelbooking.system.Entity.User;
import com.hotelbooking.system.Repository.UserRepository;
import com.hotelbooking.system.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetAllUsers() {

        User user = new User();
        user.setName("Siva");
        when(userRepository.findAll())
                .thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());

        verify(userRepository, times(1)).findAll();
    }
}
