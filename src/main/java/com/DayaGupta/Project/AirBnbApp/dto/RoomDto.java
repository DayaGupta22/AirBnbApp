package com.DayaGupta.Project.AirBnbApp.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RoomDto {
    private String type;
    private BigDecimal basePrice;
    private String[] photos;
    private String[] anenities;
    private Integer totalCount;
    private Integer capacity;
}
