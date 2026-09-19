package com.DayaGupta.Project.AirBnbApp.dto;

import com.DayaGupta.Project.AirBnbApp.entities.Booking;
import com.DayaGupta.Project.AirBnbApp.entities.User;
import com.DayaGupta.Project.AirBnbApp.entities.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private Gender gender;
    private String name;
    private Integer age;
//    private Set<Booking> bookings;

}
