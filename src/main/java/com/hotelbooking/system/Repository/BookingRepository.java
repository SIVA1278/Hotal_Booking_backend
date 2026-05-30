package com.hotelbooking.system.Repository;

import com.hotelbooking.system.Entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Bookings,Long> {

    @Query("SELECT b FROM Bookings b WHERE b.room.id = :roomId AND b.status = 'CONFIRMED' AND (b.checkInDate < :checkOut AND b.checkOutDate > :checkIn)")
    List<Bookings> findConflictingBookings(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );

    List<Bookings> findByUserIdOrderByIdDesc(Long userId);

    boolean existsByRoomIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(Long roomId, LocalDate checkOut, LocalDate checkIn);
}
