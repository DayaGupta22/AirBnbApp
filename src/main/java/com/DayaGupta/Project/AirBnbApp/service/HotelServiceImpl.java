package com.DayaGupta.Project.AirBnbApp.service;

import com.DayaGupta.Project.AirBnbApp.dto.HotelDto;
import com.DayaGupta.Project.AirBnbApp.dto.HotelnfoDto;
import com.DayaGupta.Project.AirBnbApp.dto.RoomDto;
import com.DayaGupta.Project.AirBnbApp.entities.Hotel;
import com.DayaGupta.Project.AirBnbApp.entities.Room;
import com.DayaGupta.Project.AirBnbApp.entities.User;
import com.DayaGupta.Project.AirBnbApp.exceptions.ResourceNotFoundException;
import com.DayaGupta.Project.AirBnbApp.exceptions.UnAuthorizedException;
import com.DayaGupta.Project.AirBnbApp.repositories.HotelRepository;
import com.DayaGupta.Project.AirBnbApp.repositories.RoomRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

  private final  HotelRepository hotelRepository;
  private final InventoryService inventoryService;

  private final RoomRepository roomRepository;
  private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creatin a new hotel with name :{}",hotelDto.getName());
         Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
         hotel.setActive(false);
         User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         hotel.setOwner(user);
         Hotel savedHotel=hotelRepository.save(hotel);
        log.info("Created a new hotel with name :{}",hotel.getId());
         return modelMapper.map(savedHotel ,HotelDto.class);

    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Get the hotel with id  :{}",id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->
                new ResourceNotFoundException("Hotel with id  :"+id+" not found"));
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorizedException("The hotel doesnot belong to the user with id :{}"+id);
        }
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with id :{}",id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Hotel with id  :"+id+" not found"));

        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorizedException("The hotel doesnot belong to the user with id :{}"+id);
        }

      modelMapper.map(hotelDto,hotel);
      hotel.setId(id);
      hotel = hotelRepository.save(hotel);
      log.info("Updated the hotel with id :{}",hotel.getId());
      return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Hotel with id  :"+id+" not found"));
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorizedException("The hotel doesnot belong to the user with id :{}"+id);
        }

        for(Room room :hotel.getRooms()){
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void activateHotelById(Long id) {
        log.info("Activating  the hotel with id :{}",id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Hotel with id  :"+id+" not found"));
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorizedException("The hotel doesnot belong to the user with id :{}"+id);
        }
        hotel.setActive(true);
        for(Room room : hotel.getRooms()){
            inventoryService.initializeRoomForaYear(room);
        }

    }

    // this is the public global method any user access it
    @Override
   public HotelnfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Hotel with id  :"+hotelId+" not found"));
        List<RoomDto> rooms = hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();

        return new HotelnfoDto(modelMapper.map(hotel,HotelDto.class),rooms);
    }
}
