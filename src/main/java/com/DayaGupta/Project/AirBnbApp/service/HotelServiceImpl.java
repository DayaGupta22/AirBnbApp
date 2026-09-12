package com.DayaGupta.Project.AirBnbApp.service;

import com.DayaGupta.Project.AirBnbApp.dto.HotelDto;
import com.DayaGupta.Project.AirBnbApp.entities.Hotel;
import com.DayaGupta.Project.AirBnbApp.exceptions.ResourceNotFoundException;
import com.DayaGupta.Project.AirBnbApp.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

  private final  HotelRepository hotelRepository;
  private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creatin a new hotel with name :{}",hotelDto.getName());
         Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
         hotel.setActive(false);
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
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with id :{}",id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Hotel with id  :"+id+" not found"));
      modelMapper.map(hotelDto,hotel);
      hotel.setId(id);
      hotel = hotelRepository.save(hotel);
      log.info("Updated the hotel with id :{}",hotel.getId());
      return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public boolean deleteHotelById(Long id) {
       boolean exists = hotelRepository.existsById(id);
       if(!exists) throw new ResourceNotFoundException("Hotel with id  :"+id+" not found");

       hotelRepository.deleteById(id);
       // TODO: delete the futre inventories for this hotel
        return true;
    }
}
