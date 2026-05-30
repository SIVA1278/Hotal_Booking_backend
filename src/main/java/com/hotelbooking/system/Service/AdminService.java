package com.hotelbooking.system.Service;


import com.hotelbooking.system.Repository.BookingRepository;
import com.hotelbooking.system.Repository.HotelRepository;
import com.hotelbooking.system.Repository.PaymentRepository;
import com.hotelbooking.system.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository ;

    public Map<String , Object> getDashboardData(){
        Map<String , Object> dashboard = new HashMap<>();
        dashboard.put("TotalUser",userRepository.count());
        dashboard.put("TotalHotels", hotelRepository.count());
        dashboard.put("TotalBookings", bookingRepository.count());
        dashboard.put("TotalPayments", paymentRepository.count());

        return dashboard;
    }
}
