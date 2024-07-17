package com.myhotel.beachhotel.repository;

import com.myhotel.beachhotel.domain.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {
}
