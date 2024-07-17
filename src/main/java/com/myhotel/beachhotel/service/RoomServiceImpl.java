package com.myhotel.beachhotel.service;

import com.myhotel.beachhotel.domain.Room;
import com.myhotel.beachhotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException {
        Room room = new Room();
        room.setRoomPrice(roomPrice);
        room.setRoomType(roomType);
        if(!photo.isEmpty()){
            byte[] photobytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(photobytes);
            room.setPhoto(photoBlob);
        }

        return roomRepository.save(room);

    }

    @Override
    public List<Room> getAllRooms() {
        //List<Room> room =
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.getById((id));
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Room updateRoom(Long id, MultipartFile photo, String roomType, BigDecimal roomPrice) throws Exception{
        Room existingRoom = roomRepository.getById(id);
        if(existingRoom != null) {
            existingRoom.setRoomPrice(roomPrice);
            existingRoom.setRoomType(roomType);
            if (existingRoom.getPhoto() != null) {
                byte[] photobytes = photo.getBytes();
                Blob photoBlob = new SerialBlob(photobytes);
                existingRoom.setPhoto(photoBlob);
            }
        }else{
            System.out.println("No existing room in the database");
        }
        return roomRepository.save(existingRoom);
    }
}
