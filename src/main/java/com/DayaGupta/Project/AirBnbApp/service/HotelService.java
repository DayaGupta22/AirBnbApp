package com.DayaGupta.Project.AirBnbApp.service;

import com.DayaGupta.Project.AirBnbApp.dto.HotelDto;
import com.DayaGupta.Project.AirBnbApp.dto.HotelnfoDto;
import org.jspecify.annotations.Nullable;


public interface HotelService {



    HotelDto createNewHotel(HotelDto hotelDto);

     HotelDto getHotelById(Long id);

     HotelDto updateHotelById(Long id,HotelDto hotelDto);

     void deleteHotelById(Long id);

     void activateHotelById(Long id);

     HotelnfoDto getHotelInfoById(Long hotelId) ;

}
