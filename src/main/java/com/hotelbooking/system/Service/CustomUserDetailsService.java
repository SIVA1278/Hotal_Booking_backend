package com.hotelbooking.system.Service;

import com.hotelbooking.system.Entity.User;
import com.hotelbooking.system.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found");

        }

        User existingUser= user.get();
        return new org.springframework.security.core.userdetails.User(
                existingUser.getEmail(),
                existingUser.getPassword(),

                List.of(new SimpleGrantedAuthority(existingUser.getRole().name()))

        );

    }
}

//new