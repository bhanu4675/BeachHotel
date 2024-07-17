package com.myhotel.beachhotel.service;

import com.myhotel.beachhotel.domain.Room;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Service
public interface RoomService {

    Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException;

    List<Room> getAllRooms();

    Room getRoomById(Long id);

    void deleteRoom(Long id);

    Room updateRoom(Long id, MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, Exception;

    byte[] getRoomPhotoById(Long id) throws SQLException;
}
