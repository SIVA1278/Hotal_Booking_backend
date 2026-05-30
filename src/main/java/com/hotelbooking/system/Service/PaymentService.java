package com.hotelbooking.system.Service;

import com.hotelbooking.system.DTO.PaymentRequest;
import com.hotelbooking.system.Entity.Bookings;
import com.hotelbooking.system.Entity.Payments;
import com.hotelbooking.system.Repository.BookingRepository;
import com.hotelbooking.system.Repository.PaymentRepository;
import com.hotelbooking.system.enums.BookingStatus;
import com.hotelbooking.system.enums.PaymentStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Payments savePayment(Payments payment) {
        return paymentRepository.save(payment);
    }
    @Transactional
    public Payments processPayment(PaymentRequest request) {
        Bookings booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + request.getBookingId()));

        Payments payment = new Payments();
        payment.setBooking(booking);
        payment.setAmount(request.getAmount());
        payment.setStatus(PaymentStatus.SUCCESS);

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        return paymentRepository.save(payment);
    }

    public List<Payments> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payments getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
    }

    public Payments getPaymentByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }

    public Payments createPayment(PaymentRequest request) {
        Bookings booking = bookingRepository
                .findById(request.getBookingId())
                .orElseThrow(() ->

                        new RuntimeException("Booking not found"));

        Payments payment = new Payments();
        payment.setBooking(booking);
        payment.setAmount(request.getAmount());
        payment.setStatus(PaymentStatus.SUCCESS);
        return paymentRepository.save(payment);

    }
}
