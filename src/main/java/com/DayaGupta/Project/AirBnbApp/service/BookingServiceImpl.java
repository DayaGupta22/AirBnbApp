package com.DayaGupta.Project.AirBnbApp.service;

import com.DayaGupta.Project.AirBnbApp.dto.BookingDto;
import com.DayaGupta.Project.AirBnbApp.dto.BookingRequest;
import com.DayaGupta.Project.AirBnbApp.dto.GuestDto;
import com.DayaGupta.Project.AirBnbApp.entities.*;
import com.DayaGupta.Project.AirBnbApp.entities.enums.BookingStatus;
import com.DayaGupta.Project.AirBnbApp.exceptions.ResourceNotFoundException;
import com.DayaGupta.Project.AirBnbApp.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;

    @Override
    @Transactional
    public BookingDto initializeBooking(BookingRequest bookingRequest) {

        log.info("InitializeBooking for hotel :{} ,room: {} ,date -{}-{}",bookingRequest.getHotelId(),
                bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate());

        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(()->
            new ResourceNotFoundException("Hotel not Found on Booking time with id :"+bookingRequest.getHotelId()));

        Room room = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(()->
            new ResourceNotFoundException("Room not Found on Booking time with id :"+bookingRequest.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(),bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate(),
                bookingRequest.getRoomsCount());

        long daysCount= ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate())+1;
        if(inventoryList.size() != daysCount){
            throw new IllegalStateException("Room is not available anymore");

        }
        // reserved tge room update the booked count of inventories
        for(Inventory inventory : inventoryList){
           // inventory.setBookedCount(inventory.getBookedCount() + bookingRequest.getRoomsCount());
            inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
        }
        inventoryRepository.saveAll(inventoryList);

        // create the booking

        User user =getCurrentuser();
        // TODO :calculate dynamic amount

        Booking booking =Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkedInDate(bookingRequest.getCheckInDate())
                .checkedOutdate(bookingRequest.getCheckOutDate())
                .user(user)
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        booking= bookingRepository.save(booking);
        return modelMapper.map(booking , BookingDto.class);
    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {

        log.info("Adding guests for booking id :{}",bookingId);

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->
                new ResourceNotFoundException("Booking not Found on Booking time with id :"+bookingId));

        if(hasBookingExpired(booking)){
            throw new IllegalStateException("Booking has already expired");
        }
        if(booking.getBookingStatus() != BookingStatus.RESERVED){
            throw new IllegalStateException("Guests are not available or reserved  anymore");
        }
        for(GuestDto guestDto : guestDtoList){
            Guest guest = modelMapper.map(guestDto,Guest.class);
            guest.setUser(getCurrentuser());
            guest= guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
         booking= bookingRepository.save(booking);
        return modelMapper.map(booking,BookingDto.class);
    }
    public Boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }
    public User getCurrentuser(){
        User user= new User();
        user.setId(1L); //TODO : remove dummy User
        return user;

    }


}
