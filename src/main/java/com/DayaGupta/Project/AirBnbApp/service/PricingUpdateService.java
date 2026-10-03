package com.DayaGupta.Project.AirBnbApp.service;

import com.DayaGupta.Project.AirBnbApp.entities.Hotel;
import com.DayaGupta.Project.AirBnbApp.entities.HotelMinPrice;
import com.DayaGupta.Project.AirBnbApp.entities.Inventory;
import com.DayaGupta.Project.AirBnbApp.repositories.HotelMinPriceRepository;
import com.DayaGupta.Project.AirBnbApp.repositories.HotelRepository;
import com.DayaGupta.Project.AirBnbApp.repositories.InventoryRepository;
import com.DayaGupta.Project.AirBnbApp.strategies.PricingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PricingUpdateService {
    // SCHEDULER TO update the inventory and HotelMInprice table every hour\

    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final PricingService pricingService;


    @Scheduled(cron="0 0 * * * *")
    public void updatePrice(){
         int page =0;
         int batchSize=100;
         while(true){
             Page<Hotel> hotelPage = hotelRepository.findAll(PageRequest.of(page,batchSize));
             if(hotelPage.isEmpty()){
                 break;
             }
                hotelPage.getContent().forEach(this::updateHotelPrices);
             page++;
         }

    }
    private void updateHotelPrices(Hotel hotel){

        log.info("updating hotel prices on every inventoies for hotel id;{}",hotel.getId());

        LocalDate startDate= LocalDate.now();
        LocalDate endDate= LocalDate.now().plusYears(1);
        List<Inventory> inventoryList = inventoryRepository.findByHotelAndDateBetween(hotel,startDate,endDate);
        updateInventoryPrices(inventoryList);
        updateHotelMinPrice(hotel,inventoryList,startDate,endDate);

    }

    private void updateHotelMinPrice(Hotel hotel, List<Inventory> inventoryList, LocalDate starDate, LocalDate endDate) {

        // Compute minimum price perday for the hotel
         Map<LocalDate ,BigDecimal> dailyMinPrice = inventoryList.stream()
                 .collect(Collectors.groupingBy(
                         Inventory::getDate,
                         Collectors.mapping(Inventory::getPrice , Collectors.minBy(Comparator.naturalOrder()))
                 ))
                 .entrySet().stream()
                 .collect(Collectors.toMap(Map.Entry::getKey, e-> e.getValue().orElse(BigDecimal.ZERO)));

         // Prepare HotelMInOPrice entities in bulk
         List<HotelMinPrice> hotelPrices = new ArrayList<>();
         dailyMinPrice.forEach((date,price)->{
             HotelMinPrice hotelMinPrice = hotelMinPriceRepository.findByHotelAndDate(hotel,date)
                     .orElse(new HotelMinPrice (hotel,date));
             hotelMinPrice.setPrice(price);
             hotelPrices.add(hotelMinPrice);
         });
         // save all hotelMinprice entity in  bulk
        hotelMinPriceRepository.saveAll(hotelPrices);
    }


    private void updateInventoryPrices(List<Inventory> inventoryList){
        inventoryList.forEach(inventory -> {
            BigDecimal dynamicPrice = pricingService.calculateDynamicPrice(inventory);
            inventory.setPrice(dynamicPrice);

        });
        inventoryRepository.saveAll(inventoryList);

    }

}
