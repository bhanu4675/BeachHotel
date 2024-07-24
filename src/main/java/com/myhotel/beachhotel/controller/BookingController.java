package com.myhotel.beachhotel.controller;

import com.myhotel.beachhotel.domain.BookedRoom;
import com.myhotel.beachhotel.domain.Room;
import com.myhotel.beachhotel.exception.InvalidBookingRequestException;
import com.myhotel.beachhotel.response.BookingResponse;
import com.myhotel.beachhotel.response.RoomResponse;
import com.myhotel.beachhotel.service.BookingService;
import com.myhotel.beachhotel.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    @Lazy
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    Logger logger = LoggerFactory.getLogger(BookingController.class);

    @PostMapping("/room/{roomId}/save-booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId,
                                         @RequestBody BookedRoom bookingRequest ){
        try {
            BookingResponse bookingResponse = new BookingResponse();

            logger.info("Before entering saveRoom method");
            String confirmationCode = bookingService.saveRoom(roomId, bookingRequest);

            bookingResponse.setStatus("Your Booking has done successfully. Here is your booking confirmation code " + confirmationCode);

            return ResponseEntity.ok(bookingResponse);
        }catch(Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){

        List<BookedRoom> allBookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for(BookedRoom booking : allBookings){
            BookingResponse bookingResponse = new BookingResponse();
            RoomResponse roomResponse = new RoomResponse();
            /*roomResponse = getRoomDetailsById(booking.getBookingId());
            roomResponse.getRoomPrice();
            roomResponse.getRoomType()
            bookingResponse.setRoom(roomResponse);*/

            bookingResponse.setId(booking.getBookingId());
            bookingResponse.setCheckInDate(booking.getCheckInDate());
            bookingResponse.setCheckOutDate(booking.getCheckOutDate());
            bookingResponse.setGuestEmail(booking.getGuestEmailAddress());
            bookingResponse.setBookingConfirmationCode(booking.getBookingConfirmationCode());
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @PostMapping("/{roomId}/cancel-booking")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long roomId) throws Exception {

            BookingResponse bookingResponse = new BookingResponse();
            BookedRoom bookedRoom = bookingService.getBookingById(roomId);
            bookingResponse.setStatus("Your booking has been cancelled.");
        if(bookedRoom.getBookingId().equals(roomId)){
            bookingService.deleteBooking(roomId);

        }else{
            throw new Exception("Please give the valid inputs to proceed...");
        }
        return ResponseEntity.ok(bookingResponse);
    }

    @GetMapping("/get-booking-by-confirmationCode")
    public ResponseEntity<BookingResponse> getBookingByConfirmationCode(@RequestParam("bookingConfirmationCode") String bookingConfirmationCode) throws Exception {
        BookingResponse bookingResponse = new BookingResponse();

            //check if the confirmation code is valid 10 digit numeric code
            Boolean validConfirmationCode = checkForValidConfirmationCode(bookingConfirmationCode);
            if (validConfirmationCode) {
                BookedRoom bookedRoom = bookingService.getBookingByConfirmationCode(bookingConfirmationCode);
                bookingResponse.setId(bookedRoom.getBookingId());
                bookingResponse.setCheckInDate(bookedRoom.getCheckInDate());
                bookingResponse.setCheckOutDate(bookedRoom.getCheckOutDate());
                bookingResponse.setGuestEmail(bookedRoom.getGuestEmailAddress());
                bookingResponse.setBookingConfirmationCode(bookedRoom.getBookingConfirmationCode());
                Room room = roomService.getRoomById(bookedRoom.getBookingId());
                RoomResponse roomResponse = new RoomResponse();
                roomResponse.setRoomType(room.getRoomType());
                roomResponse.setRoomPrice(room.getRoomPrice());
                roomResponse.setBooked(room.isBooked());
                bookingResponse.setRoom(roomResponse);

            } else {
                throw new Exception("Please give the valid 10 digit confirmation code and try again");
            }
            return ResponseEntity.ok(bookingResponse);

    }

    private Boolean checkForValidConfirmationCode(String confirmationCode) {
        String regex = "[0-9]+";
            Pattern pattern = Pattern.compile(regex);
            if(pattern.matcher(confirmationCode).matches()){
                return true;
            }else{
                return false;
            }
    }
}
