package com.hotelbooking.system.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingRequest {
    private Long userId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
