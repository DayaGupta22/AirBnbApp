package com.DayaGupta.Project.AirBnbApp.strategies;

import com.DayaGupta.Project.AirBnbApp.entities.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;


@RequiredArgsConstructor

public class OccupanyPricingStrategy  implements PricingStrategy{
    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
       BigDecimal price =wrapped.calculatePrice(inventory);
       double occupanyRate =(double) inventory.getBookedCount() /inventory.getTotalCount();
       if(occupanyRate > 0.8){
           price = price.multiply(BigDecimal.valueOf(1.2));
       }
       return price;
    }
}
