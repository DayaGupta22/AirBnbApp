package com.DayaGupta.Project.AirBnbApp.controller;

import com.DayaGupta.Project.AirBnbApp.dto.RoomDto;
import com.DayaGupta.Project.AirBnbApp.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/hotels/{hotelId}/rooms")
public class RoomAdminController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId,
                                                 @RequestBody RoomDto roomDto){
    RoomDto room=roomService.createNewRoom(hotelId,roomDto);
    return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms(@PathVariable Long hotelId){
        return ResponseEntity.ok(roomService. getAllRoomsInHotel(hotelId));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId){
        return ResponseEntity.ok(roomService. getRoomById(roomId));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId){
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }

}
