package com.hotelbooking.system.controller;

import com.hotelbooking.system.Entity.Bookings;
import com.hotelbooking.system.Entity.Payments;
import com.hotelbooking.system.Repository.BookingRepository;
import com.hotelbooking.system.Service.PaymentService;
import com.hotelbooking.system.enums.PaymentStatus;
import com.hotelbooking.system.enums.BookingStatus;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaymentController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private com.hotelbooking.system.Service.EmailService emailService;

    @GetMapping("/user/payment/{bookingId}")
    public String paymentPage(@PathVariable Long bookingId,
                              Model model) {

        Payments payment = new Payments();

        Bookings booking =
                bookingRepository.findById(bookingId)
                        .orElse(null);

        if (booking != null && booking.getRoom() != null) {

            payment.setBooking(booking);

            payment.setAmount(
                    booking.getRoom().getPrice()
            );
        }

        model.addAttribute("payment", payment);

        return "payment";
    }

    @PostMapping("/user/process-payment")
    public String processPayment(
            @RequestParam("bookingId") Long bookingId,
            @RequestParam("amount") double amount) {

        System.out.println("DEBUG: SIMPLE PROCESS-PAYMENT START - BookingID: " + bookingId);
        try {
            Bookings booking = bookingRepository.findById(bookingId).orElse(null);
            if (booking != null) {
                booking.setStatus(BookingStatus.CONFIRMED);
                bookingRepository.save(booking);
                System.out.println("DEBUG: Booking " + bookingId + " Confirmed!");

                Payments payment = paymentService.getPaymentByBookingId(bookingId);
                if (payment == null) {
                    payment = new Payments();
                    payment.setBooking(booking);
                }
                payment.setAmount(amount);
                payment.setStatus(PaymentStatus.SUCCESS);
                paymentService.savePayment(payment);
                System.out.println("DEBUG: Payment Saved!");
                
                try {
                    emailService.sendBookingEmail(booking);
                    System.out.println("DEBUG: Confirmation email sent to " + booking.getUser().getEmail());
                } catch (Exception mailEx) {
                    System.out.println("DEBUG: Failed to send email: " + mailEx.getMessage());
                }
            }
            
            return "redirect:/user/my-bookings?success=true";

        } catch (Exception e) {
            System.out.println("DEBUG: ERROR: " + e.getMessage());
            return "redirect:/user/my-bookings?error=true";
        }
    }
}