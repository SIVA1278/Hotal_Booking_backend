package com.hotelbooking.system.Service;

import com.hotelbooking.system.Entity.User;
import com.hotelbooking.system.Repository.UserRepository;
import com.hotelbooking.system.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for general User management.
 * Handles operations like user retrieval, registration, and deletion.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user with validation for duplicate emails.
     */
    public User registerUser(User user) {
        // Check if email is already taken
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already registered: " + user.getEmail());
        }

        // Encrypt the password and set default role
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
