package com.DayaGupta.Project.AirBnbApp.repositories;

import com.DayaGupta.Project.AirBnbApp.entities.Hotel;
import com.DayaGupta.Project.AirBnbApp.entities.Inventory;
import com.DayaGupta.Project.AirBnbApp.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    void deleteByDateAfterAndRoom(LocalDate date, Room room);
}
