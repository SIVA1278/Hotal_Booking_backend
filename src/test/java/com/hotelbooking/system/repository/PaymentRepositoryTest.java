package com.hotelbooking.system.repository;

import com.hotelbooking.system.Entity.Bookings;
import com.hotelbooking.system.Entity.Payments;
import com.hotelbooking.system.Repository.BookingRepository;
import com.hotelbooking.system.Repository.PaymentRepository;
import com.hotelbooking.system.controller.BookingControllerTest;
import com.hotelbooking.system.enums.BookingStatus;
import com.hotelbooking.system.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void testSavePayment() {

        Bookings booking = new Bookings();
        booking.setCheckInDate(LocalDate.now());
        booking.setCheckOutDate(LocalDate.now().plusDays(2));
        booking.setStatus(BookingStatus.CONFIRMED);

        Bookings savedBooking = bookingRepository.save(booking);

        Payments payment = new Payments();
        payment.setBooking(savedBooking);
        payment.setAmount(5000.0);
        payment.setStatus(PaymentStatus.SUCCESS);

        Payments savedPayment = paymentRepository.save(payment);

        assertNotNull(savedPayment.getId());
        assertEquals(5000.0, savedPayment.getAmount());
        assertEquals(PaymentStatus.SUCCESS, savedPayment.getStatus());
    }

    @Test
    void testGetPaymentById() {

        Bookings booking = new Bookings();
        booking.setCheckInDate(LocalDate.now());
        booking.setCheckOutDate(LocalDate.now().plusDays(1));
        booking.setStatus(BookingStatus.CONFIRMED);

        Bookings savedBooking = bookingRepository.save(booking);

        Payments payment = new Payments();
        payment.setBooking(savedBooking);
        payment.setAmount(3000.0);
        payment.setStatus(PaymentStatus.SUCCESS);

        Payments savedPayment = paymentRepository.save(payment);

        Payments foundPayment = paymentRepository
                .findById(savedPayment.getId())
                .orElse(null);

        assertNotNull(foundPayment);
        assertEquals(3000.0, foundPayment.getAmount());
    }

    @Test
    void testDeletePayment() {

        Bookings booking = new Bookings();
        booking.setCheckInDate(LocalDate.now());
        booking.setCheckOutDate(LocalDate.now().plusDays(3));
        booking.setStatus(BookingStatus.CONFIRMED);

        Bookings savedBooking = bookingRepository.save(booking);

        Payments payment = new Payments();
        payment.setBooking(savedBooking);
        payment.setAmount(7000.0);
        payment.setStatus(PaymentStatus.SUCCESS);
        Payments savedPayment = paymentRepository.save(payment);
        paymentRepository.delete(savedPayment);
        Payments deletedPayment = paymentRepository
                .findById(savedPayment.getId())
                .orElse(null);

        assertNull(deletedPayment);
    }
}