package com.myhotel.beachhotel.repository;

import com.myhotel.beachhotel.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
