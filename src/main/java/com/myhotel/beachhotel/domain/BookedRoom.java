package com.myhotel.beachhotel.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookedRoom {

    //private Long bookingId;
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(name = "checkin_Date")
    private LocalDate checkInDate;

    @Column(name = "checkout_Date")
    private LocalDate checkOutDate;

    @Column(name = "guest_FullName")
    private String guestFullName;

    @Column(name = "guest_EmailAddress")
    private String guestEmailAddress;

    @Column(name = "guest_PhoneNumber")
    private String guestPhoneNumber;

    @Column(name = "guest_Address")
    private String guestAddress;

    @Column(name = "Num_Of_Adults")
    private int numOfAdults;

    @Column(name = "Num_Of_Children")
    private int numOfChildren;

    @Column(name = "total_Num_Of_Guests")
    private int totalNumOfGuests;

    @Column(name = "booking_Confirmation")
    private String bookingConfirmationCode;

    @JoinColumn(name = "room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;


    public void calculateTotalNumOfGuests() {
        this.totalNumOfGuests = this.numOfAdults + this.numOfChildren;
    }

    public void setNumOfAdults(int numOfAdults) {
        numOfAdults = numOfAdults;
        calculateTotalNumOfGuests();
    }

    public void setNumOfChildren(int numOfChildren) {
        numOfChildren = numOfChildren;
        calculateTotalNumOfGuests();
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode){
        bookingConfirmationCode = bookingConfirmationCode;
    }


}
