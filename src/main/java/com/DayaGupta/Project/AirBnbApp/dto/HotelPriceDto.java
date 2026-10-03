package com.DayaGupta.Project.AirBnbApp.dto;

import com.DayaGupta.Project.AirBnbApp.entities.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelPriceDto {
    private Hotel hotel;
    private Double price;

}
