package com.DayaGupta.Project.AirBnbApp.controller;

import com.DayaGupta.Project.AirBnbApp.dto.HotelDto;
import com.DayaGupta.Project.AirBnbApp.dto.HotelSearchRequest;
import com.DayaGupta.Project.AirBnbApp.dto.HotelnfoDto;
import com.DayaGupta.Project.AirBnbApp.service.HotelService;
import com.DayaGupta.Project.AirBnbApp.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {
    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
        Page<HotelDto>page=inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }
    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelnfoDto> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }

}
