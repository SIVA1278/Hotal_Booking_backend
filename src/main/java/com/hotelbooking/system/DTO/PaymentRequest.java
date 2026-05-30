package com.hotelbooking.system.DTO;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long bookingId;
    private double amount;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
}
