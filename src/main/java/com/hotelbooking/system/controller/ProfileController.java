package com.hotelbooking.system.controller;

import org.springframework.ui.Model;
import com.hotelbooking.system.Entity.User;
import com.hotelbooking.system.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired

    private PasswordEncoder passwordEncoder;

    @GetMapping("/user/profile")
    public String profilePage(Authentication authentication,

                              Model model) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElse(null);

        model.addAttribute("user", user);        return "profile";

    }

    @PostMapping("/user/update-profile")
    public String updateProfile(@ModelAttribute User user) {

        User existingUser =

                userRepository.findById(user.getId())

                        .orElse(null);

        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setPassword(
                    passwordEncoder.encode(user.getPassword())
            );
            userRepository.save(existingUser);

        }
        return "redirect:/user/profile";

    }

}
