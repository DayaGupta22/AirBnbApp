package com.DayaGupta.Project.AirBnbApp.service;

import com.DayaGupta.Project.AirBnbApp.dto.HotelDto;
import com.DayaGupta.Project.AirBnbApp.dto.HotelSearchRequest;

import com.DayaGupta.Project.AirBnbApp.entities.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializeRoomForaYear(Room room);
    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
