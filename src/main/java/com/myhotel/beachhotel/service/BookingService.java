package com.myhotel.beachhotel.service;

import com.myhotel.beachhotel.domain.BookedRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {

    String saveRoom(Long roomId, BookedRoom bookingRequest) throws Exception;

    List<BookedRoom> getAllBookings();

    BookedRoom getBookingById(Long roomId);

    void deleteBooking(Long roomId);

    BookedRoom getBookingByConfirmationCode(String bookingConfirmationCode);
}
