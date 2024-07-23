package com.myhotel.beachhotel.repository;

import com.myhotel.beachhotel.domain.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookedRoom, Long> {

    BookedRoom getBookingByConfirmationCode(String bookingConfirmationCode);
}
