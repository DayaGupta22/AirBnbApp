package com.DayaGupta.Project.AirBnbApp.service;

import com.DayaGupta.Project.AirBnbApp.dto.BookingDto;
import com.DayaGupta.Project.AirBnbApp.dto.BookingRequest;
import com.DayaGupta.Project.AirBnbApp.dto.GuestDto;
import com.DayaGupta.Project.AirBnbApp.entities.Hotel;
import com.DayaGupta.Project.AirBnbApp.entities.Room;
import com.DayaGupta.Project.AirBnbApp.entities.User;
import com.DayaGupta.Project.AirBnbApp.entities.enums.BookingStatus;
import com.DayaGupta.Project.AirBnbApp.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {


//    private Hotel hotel;
//    private Room room;
//    private User user;
//    private LocalDateTime createdAt;
//    private LocalDate checkedInDate;
//    private LocalDate checkedOutdate;
//    private Integer roomsCount;
//    private BookingStatus bookingStatus;
//    private Set<GuestDto> guests;
    private final BookingRepository bookingRepository;
    @Override
    public BookingDto initializeBooking(BookingRequest bookingRequest) {
return null;
    }
}
