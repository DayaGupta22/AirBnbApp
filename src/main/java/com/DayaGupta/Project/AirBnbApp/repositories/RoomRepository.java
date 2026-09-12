package com.DayaGupta.Project.AirBnbApp.repositories;

import com.DayaGupta.Project.AirBnbApp.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
