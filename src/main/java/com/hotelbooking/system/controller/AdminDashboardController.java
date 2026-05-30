package com.hotelbooking.system.controller;

import com.hotelbooking.system.Repository.BookingRepository;
import com.hotelbooking.system.Repository.HotelRepository;
import com.hotelbooking.system.Repository.PaymentRepository;
import com.hotelbooking.system.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {


        @Autowired
        private UserRepository userRepository;

        @Autowired
        private HotelRepository hotelRepository;

        @Autowired
        private BookingRepository bookingRepository;

        @Autowired
        private PaymentRepository paymentRepository;

        @GetMapping("/admin/dashboard")
        public String dashboard(Model model) {

            model.addAttribute("totalUsers",
                    userRepository.count());

            model.addAttribute("totalHotels",
                    hotelRepository.count());

            model.addAttribute("totalBookings",
                    bookingRepository.count());

            model.addAttribute("totalPayments",
                    paymentRepository.count());

            return "admin-dashboard";
        }

        @GetMapping("/admin/all-bookings")
        public String allBookings(Model model) {
            model.addAttribute("bookings", bookingRepository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "id")));
            return "admin-bookings";
        }
    }

