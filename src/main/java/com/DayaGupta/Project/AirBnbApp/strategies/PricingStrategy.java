package com.DayaGupta.Project.AirBnbApp.strategies;

import com.DayaGupta.Project.AirBnbApp.entities.Inventory;

import java.math.BigDecimal;


public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);
}
