package com.DayaGupta.Project.AirBnbApp.strategies;

import com.DayaGupta.Project.AirBnbApp.entities.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingService {
    public BigDecimal calculateDynamicPrice(Inventory inventory){
        PricingStrategy pricingStrategy = new BasePricingStrategy();
        // Apply the additional strategy
        pricingStrategy = new SurgePricingStrategy(pricingStrategy);
        pricingStrategy = new OccupanyPricingStrategy(pricingStrategy);
        pricingStrategy = new UregencyPricingStrategy(pricingStrategy);
        pricingStrategy= new HolidayPricingStrategy(pricingStrategy);
        return pricingStrategy.calculatePrice(inventory);
    }
}
