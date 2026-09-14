package com.DayaGupta.Project.AirBnbApp.service;

import com.DayaGupta.Project.AirBnbApp.entities.Room;

public interface InventoryService {
    void initializeRoomForaYear(Room room);
    void deleteFutureInventories(Room room);
}
