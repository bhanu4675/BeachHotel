package com.myhotel.beachhotel.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestFullName;
    private String guestEmail;

    private int numOfGuests;

    private String bookingConfirmationCode;

    private RoomResponse room;

    private String status;

    public BookingResponse(Long id, LocalDate checkInDate, LocalDate checkOutDate, int numOfGuests, String bookingConfirmationCode) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numOfGuests = numOfGuests;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public BookingResponse(Long id, LocalDate checkInDate, LocalDate checkOutDate, String guestFullName, int numOfGuests, String bookingConfirmationCode) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guestFullName = guestFullName;
        this.numOfGuests = numOfGuests;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public BookingResponse(String status){
        this.status = status;
    }
}
