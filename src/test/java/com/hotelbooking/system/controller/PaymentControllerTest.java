package com.hotelbooking.system.controller;

import com.hotelbooking.system.Entity.Bookings;
import com.hotelbooking.system.Entity.Payments;
import com.hotelbooking.system.Entity.Rooms;
import com.hotelbooking.system.Repository.BookingRepository;
import com.hotelbooking.system.Service.PaymentService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private com.hotelbooking.system.Service.EmailService emailService;

    @Test
    void testPaymentPage() throws Exception {

        Rooms room = new Rooms();
        room.setPrice(5000.0);

        Bookings booking = new Bookings();
        booking.setRoom(room);

        when(bookingRepository.findById(1L))
                .thenReturn(Optional.of(booking));

        mockMvc.perform(get("/user/payment/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment"));
    }

    @Test
    void testProcessPayment() throws Exception {
        Payments payment = new Payments();
        payment.setAmount(5000.0);

        when(paymentService.savePayment(any(Payments.class)))
                .thenReturn(payment);

        mockMvc.perform(post("/user/process-payment")
                        .param("bookingId", "1")
                        .param("amount", "5000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/my-bookings?success=true"));
    }
}