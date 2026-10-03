package com.DayaGupta.Project.AirBnbApp.strategies;

import com.DayaGupta.Project.AirBnbApp.entities.Inventory;

import java.math.BigDecimal;

public class BasePricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
