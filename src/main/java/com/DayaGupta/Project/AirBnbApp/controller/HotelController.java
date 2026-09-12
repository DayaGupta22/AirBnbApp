package com.DayaGupta.Project.AirBnbApp.controller;

import com.DayaGupta.Project.AirBnbApp.dto.HotelDto;
import com.DayaGupta.Project.AirBnbApp.entities.Hotel;
import com.DayaGupta.Project.AirBnbApp.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto) {
        log.info("attempting to a crate a new hotel with name :{}",hotelDto.getName());
       HotelDto hotel= hotelService.createNewHotel(hotelDto);
       return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }
    @GetMapping("/{hotelid}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelid){
        HotelDto hotel = hotelService.getHotelById(hotelid);
        return ResponseEntity.ok(hotel);
    }
    @PutMapping("/{hotelid}")
    public ResponseEntity<HotelDto>updateHotelById(
            @PathVariable Long hotelid,
            @RequestBody HotelDto hotelDto){
       HotelDto hotel= hotelService.updateHotelById(hotelid,hotelDto);
       return ResponseEntity.ok(hotel);

    }
    @DeleteMapping("/{hotelid}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelid){
       hotelService.deleteHotelById(hotelid);
        return ResponseEntity.noContent().build();
    }


}
