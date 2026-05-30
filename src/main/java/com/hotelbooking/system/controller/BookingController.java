package com.hotelbooking.system.controller;

import com.hotelbooking.system.Entity.Bookings;
import com.hotelbooking.system.Entity.Rooms;
import com.hotelbooking.system.Entity.User;
import com.hotelbooking.system.Repository.RoomRepository;
import com.hotelbooking.system.Repository.UserRepository;
import com.hotelbooking.system.Service.BookingService;
import com.hotelbooking.system.Service.EmailService;
import com.hotelbooking.system.enums.BookingStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/user/book-room/{roomId}")
    public String bookingPage(@PathVariable Long roomId,
                              Model model) {

        Bookings booking = new Bookings();

        Rooms room = roomRepository
                .findById(roomId)
                .orElse(null);

        booking.setRoom(room);

        model.addAttribute("booking", booking);

        return "booking";
    }

    @PostMapping("/user/save-booking")
    public String saveBooking(@ModelAttribute("booking") Bookings booking,
                              @RequestParam("roomId") Long roomId,
                              Authentication authentication,
                              Model model) {
        System.out.println("DEBUG: Saving booking for room ID: " + roomId);
        try {
            Rooms room = roomRepository.findById(roomId).orElse(null);
            if (room == null) {
                model.addAttribute("error", "Invalid Room selected");
                return "redirect:/hotels";
            }
            booking.setRoom(room);

            if (booking.getCheckOutDate().isBefore(booking.getCheckInDate())) {
                model.addAttribute("error", "Check-out date must be after check-in date");
                return "booking";
            }

            java.time.LocalDate threeMonthsFromNow = java.time.LocalDate.now().plusMonths(3);
            if (booking.getCheckInDate().isAfter(threeMonthsFromNow) || booking.getCheckOutDate().isAfter(threeMonthsFromNow)) {
                model.addAttribute("error", "Bookings can only be made up to 3 months in advance.");
                return "booking";
            }

            boolean exists = bookingService.isRoomAvailable(room.getId(), booking.getCheckInDate(), booking.getCheckOutDate());
            if (!exists) {
                model.addAttribute("error", "This room is already reserved for these dates.");
                return "booking";
            }

            String email = authentication.getName();
            User user = userRepository.findByEmail(email).orElse(null);
            booking.setUser(user);
            booking.setStatus(BookingStatus.PENDING); // Set to PENDING initially

            Bookings savedBooking = bookingService.saveBooking(booking);
            System.out.println("DEBUG: Booking saved successfully!");

            return "redirect:/user/payment/" + savedBooking.getId();
        } catch (Exception e) {
            System.out.println("DEBUG: ERROR SAVING BOOKING: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Could not complete booking: " + e.getMessage());
            return "booking";
        }
    }

    @GetMapping("/user/my-bookings")
    public String myBookings(Model model,
                             Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        model.addAttribute("bookings", bookingService.getBookingsByUser(user.getId()));
        return "my-bookings";
    }

    @GetMapping("/user/cancel-booking/{id}")
    public String cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return "redirect:/user/my-bookings";
    }
}