package com.DayaGupta.Project.AirBnbApp.dto;

import com.DayaGupta.Project.AirBnbApp.entities.Hotel;
import com.DayaGupta.Project.AirBnbApp.entities.Room;
import com.DayaGupta.Project.AirBnbApp.entities.User;
import com.DayaGupta.Project.AirBnbApp.entities.enums.BookingStatus;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private Hotel hotel;
    private Room room;
    private User user;
    private LocalDateTime createdAt;
    private LocalDate checkedInDate;
    private LocalDate checkedOutdate;
    private Integer roomsCount;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;

}
