package com.DayaGupta.Project.AirBnbApp.controller;

import com.DayaGupta.Project.AirBnbApp.dto.BookingDto;
import com.DayaGupta.Project.AirBnbApp.dto.BookingRequest;
import com.DayaGupta.Project.AirBnbApp.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class HotelBookingController {
    private final BookingService bookingService;
    @PostMapping
    public ResponseEntity<BookingDto> initializeBooking(@RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.ok().body(bookingService.initializeBooking(bookingRequest));
    }
}

