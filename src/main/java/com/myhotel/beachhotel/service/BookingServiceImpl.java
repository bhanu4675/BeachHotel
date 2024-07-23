package com.myhotel.beachhotel.service;

import com.myhotel.beachhotel.domain.BookedRoom;
import com.myhotel.beachhotel.domain.Room;
import com.myhotel.beachhotel.exception.InvalidBookingRequestException;
import com.myhotel.beachhotel.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("bookingService")
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;


    @Autowired
    private RoomService roomService;

    //@Qualifier("bookingService")
    @Autowired
    @Lazy
    private BookingService bookingService;


    @Override
    public String saveRoom(Long roomId, BookedRoom bookingRequest) throws Exception {
        String confirmationCode = null;

        //date validation
        if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new InvalidBookingRequestException("Check Out date must be after Checkin date...");
        }else{
            //first get the room and details
            //check if the room is availble or not from bookedRoom with existingbookings
            //check roomAvailble method by using dates, use all possible scenarios
            //use boolean --> if not booked then save it else throw exceptin msg
            Room room = new Room();
            room = roomService.getRoomById(roomId);
            List<BookedRoom> existingBookings = bookingService.getAllBookings();
            Boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);

            if(roomIsAvailable){
               room.addBooking(bookingRequest);
            }else{
                throw new InvalidBookingRequestException("Room is not available. Please choose another one");
            }
        }
        bookingRepository.save(bookingRequest);

        return bookingRequest.getBookingConfirmationCode();
    }

    private Boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        //add conditions as necessary with check in and check out dates comparision
        return existingBookings.stream().noneMatch(existingBooking -> existingBooking.getRoom().equals(bookingRequest.getRoom()));
    }

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public BookedRoom getBookingById(Long roomId) {
        return bookingRepository.getById(roomId);
    }

    @Override
    public void deleteBooking(Long roomId) {
        bookingRepository.deleteById(roomId);
    }

    @Override
    public BookedRoom getBookingByConfirmationCode(String bookingConfirmationCode) {
        return bookingRepository.getByBookingConfirmationCode(bookingConfirmationCode);
    }
}
