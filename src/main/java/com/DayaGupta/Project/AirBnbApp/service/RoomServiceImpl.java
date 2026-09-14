package com.DayaGupta.Project.AirBnbApp.service;

import com.DayaGupta.Project.AirBnbApp.dto.RoomDto;
import com.DayaGupta.Project.AirBnbApp.entities.Hotel;
import com.DayaGupta.Project.AirBnbApp.entities.Room;
import com.DayaGupta.Project.AirBnbApp.exceptions.ResourceNotFoundException;
import com.DayaGupta.Project.AirBnbApp.repositories.HotelRepository;
import com.DayaGupta.Project.AirBnbApp.repositories.InventoryRepository;
import com.DayaGupta.Project.AirBnbApp.repositories.RoomRepository;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final  InventoryService inventoryService;
    private final ModelMapper modelMapper;
    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating the room in Hotel :{}",hotelId);
        // find the hotel by hotel entity

       Hotel hotel = hotelRepository
               .findById(hotelId)
               .orElseThrow(() ->
               new ResourceNotFoundException("Hotel Not found with Id:"+hotelId));
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        //TODO ::create inventory as soon as room is created and oif hotel is active
        if(hotel.getActive()){
            inventoryService.initializeRoomForaYear(room);
        }
        log.info("Room created  :{}",room.getId());
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
      log.info("Getting all rooms in Hotel :{}",hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Hotel Not found with Id:"+hotelId));
        return hotel.getRooms()
                .stream()
                .map((element) ->
                        modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
      log.info("Getting room in Room :{}",roomId);
      Room room = roomRepository.findById(roomId).orElseThrow(()->
              new ResourceNotFoundException("Room Not found with Id:"+roomId));
      return modelMapper.map(room,RoomDto.class);
    }

    @Transactional
    @Override
    public void deleteRoomById(Long roomId) {
        log.info("Deleting room in Room :{}",roomId);
        Room room = roomRepository.findById(roomId).orElseThrow(()->
                new ResourceNotFoundException("Room Not found with Id:"+roomId));
        log.info("Room deleted  :{}",roomId);
        inventoryService.deleteFutureInventories(room);
        roomRepository.deleteById(roomId);
        // TODO :Delete all features inventory ofr this room


    }
}
