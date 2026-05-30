package com.hotelbooking.system.Service;

import com.hotelbooking.system.DTO.BookingRequest;
import com.hotelbooking.system.Entity.Bookings;
import com.hotelbooking.system.Entity.Rooms;
import com.hotelbooking.system.Entity.User;
import com.hotelbooking.system.Repository.BookingRepository;
import com.hotelbooking.system.Repository.RoomRepository;
import com.hotelbooking.system.Repository.UserRepository;
import com.hotelbooking.system.enums.BookingStatus;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for handling all booking-related business logic.
 * The Service layer acts as a bridge between Controllers and Repositories.
 */
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    /**
     * Logic to create a new booking.
     * Includes validation for user existence,
     * room existence, and availability.
     */
    public Bookings createBooking(BookingRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with ID: "
                                        + request.getUserId()));

        Rooms rooms = roomRepository.findById(request.getRoomId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Room not found with ID: "
                                        + request.getRoomId()));

        List<Bookings> conflicts =
                bookingRepository.findConflictingBookings(
                        rooms.getId(),
                        request.getCheckInDate(),
                        request.getCheckOutDate());

        if (!conflicts.isEmpty()) {
            throw new RuntimeException(
                    "The selected room is not available for these dates.");
        }

        Bookings booking = new Bookings();

        booking.setUser(user);
        booking.setRoom(rooms);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setStatus(BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }


    public List<Bookings> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserIdOrderByIdDesc(userId);
    }


    public Bookings cancelBooking(Long bookingId) {

        Bookings bookings =
                bookingRepository.findById(bookingId)
                        .orElseThrow(() ->
                                new RuntimeException("Booking not found"));

        bookings.setStatus(BookingStatus.CANCELLED);

        return bookingRepository.save(bookings);
    }


    public List<Bookings> getAllBookings() {
        return bookingRepository.findAll();
    }


    public Bookings saveBooking(Bookings booking) {
        return bookingRepository.save(booking);
    }


    public boolean isRoomAvailable(Long roomId,
                                   LocalDate checkIn,
                                   LocalDate checkOut) {

        return !bookingRepository
                .existsByRoomIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
                        roomId,
                        checkOut,
                        checkIn
                );
    }
}