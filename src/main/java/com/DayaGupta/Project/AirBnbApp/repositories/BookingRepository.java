package com.DayaGupta.Project.AirBnbApp.repositories;

import com.DayaGupta.Project.AirBnbApp.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}