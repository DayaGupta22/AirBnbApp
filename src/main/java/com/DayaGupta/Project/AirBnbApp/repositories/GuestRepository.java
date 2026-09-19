package com.DayaGupta.Project.AirBnbApp.repositories;

import com.DayaGupta.Project.AirBnbApp.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}