package com.DayaGupta.Project.AirBnbApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelnfoDto {
    private HotelDto hotelDto;
    private List<RoomDto> rooms;


}
