package com.DayaGupta.Project.AirBnbApp.repositories;

import com.DayaGupta.Project.AirBnbApp.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Hotel,Long> {
}
