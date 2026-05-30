package com.hotelbooking.system.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@org.springframework.stereotype.Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendBookingEmail(com.hotelbooking.system.Entity.Bookings booking) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(booking.getUser().getEmail());
        message.setSubject("Hotel Booking Confirmation - Luxe Stay");
        
        String hotelName = booking.getRoom().getHotel().getName();
        String city = booking.getRoom().getHotel().getCity();
        String roomNum = booking.getRoom().getRoomNumber();
        String checkIn = booking.getCheckInDate().toString();
        String checkOut = booking.getCheckOutDate().toString();
        
        String emailText = "Hello " + booking.getUser().getName() + ",\n\n"
                + "Your booking is confirmed!\n\n"
                + "Hotel: " + hotelName + "\n"
                + "City: " + city + "\n"
                + "Room: " + roomNum + "\n"
                + "Check-In: " + checkIn + "\n"
                + "Check-Out: " + checkOut + "\n\n"
                + "Thank you for choosing Luxe Stay!";
                
        message.setText(emailText);
        mailSender.send(message);

    }

}