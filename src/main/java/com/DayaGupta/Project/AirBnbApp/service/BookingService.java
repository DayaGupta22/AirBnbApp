package com.DayaGupta.Project.AirBnbApp.service;

import com.DayaGupta.Project.AirBnbApp.dto.BookingDto;
import com.DayaGupta.Project.AirBnbApp.dto.BookingRequest;
import org.jspecify.annotations.Nullable;

public interface BookingService {

    BookingDto initializeBooking(BookingRequest bookingRequest);
}
