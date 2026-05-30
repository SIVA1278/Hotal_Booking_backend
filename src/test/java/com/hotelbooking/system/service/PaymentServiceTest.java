package com.hotelbooking.system.service;

import com.hotelbooking.system.DTO.PaymentRequest;
import com.hotelbooking.system.Entity.Bookings;
import com.hotelbooking.system.Entity.Payments;
import com.hotelbooking.system.Repository.BookingRepository;
import com.hotelbooking.system.Repository.PaymentRepository;
import com.hotelbooking.system.Service.PaymentService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void testCreatePayment() {

        Bookings booking = new Bookings();

        booking.setId(1L);

        PaymentRequest request =
                new PaymentRequest();

        request.setBookingId(1L);

        request.setAmount(5000.0);

        when(bookingRepository.findById(1L))
                .thenReturn(Optional.of(booking));

        when(paymentRepository.save(any(Payments.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Payments payment =
                paymentService.createPayment(request);

        assertNotNull(payment);

        verify(paymentRepository, times(1))
                .save(any(Payments.class));
    }
}