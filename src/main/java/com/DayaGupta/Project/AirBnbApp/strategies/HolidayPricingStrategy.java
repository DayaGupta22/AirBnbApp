package com.DayaGupta.Project.AirBnbApp.strategies;

import com.DayaGupta.Project.AirBnbApp.entities.Inventory;
import lombok.RequiredArgsConstructor;


import java.math.BigDecimal;


@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy{
    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);

        boolean isTodayHoliday=true; // call API or check with local data
        if(isTodayHoliday){
            price = price.multiply(BigDecimal.valueOf(1.25));
        }
        return price;
    }
}
