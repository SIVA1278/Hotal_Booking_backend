package com.hotelbooking.system.Service;

import com.hotelbooking.system.Entity.User;
import com.hotelbooking.system.Repository.UserRepository;
import com.hotelbooking.system.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for handling authentication-related tasks like user registration.
 * Note: Login is handled automatically by Spring Security's formLogin().
 */
@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user with the default USER role.
     * Encodes the password before saving for security.
     */
    public void register(String name, String email, String password) {
        // Create a new User entity
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        
        // Always encode passwords! Never store them in plain text.
        user.setPassword(passwordEncoder.encode(password));
        
        // Assign the default role
        user.setRole(Role.USER);
        
        // Save the user to the database
        userRepository.save(user);
    }
}
