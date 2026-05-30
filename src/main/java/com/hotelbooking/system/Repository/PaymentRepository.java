package com.hotelbooking.system.Repository;

import com.hotelbooking.system.Entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository  extends JpaRepository<Payments ,Long> {
    Payments findByBookingId(Long bookingId);
}
