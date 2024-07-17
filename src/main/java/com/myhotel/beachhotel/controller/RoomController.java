package com.myhotel.beachhotel.controller;

import com.myhotel.beachhotel.domain.Room;
import com.myhotel.beachhotel.response.RoomResponse;
import com.myhotel.beachhotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/add/new-room")
    public ResponseEntity<RoomResponse> addRoom(@RequestParam("file") MultipartFile file,
                                  @RequestParam("roomType") String roomType,
                                  @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {

        Room savedRoom = roomService.addNewRoom(file,roomType,roomPrice);
        RoomResponse roomResponse= new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());

        return ResponseEntity.ok(roomResponse);
    }

    @GetMapping("/all-rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        RoomResponse roomResponse = new RoomResponse();
        List<RoomResponse> roomListResponse= new ArrayList<>();
        List<Room> roomsList = roomService.getAllRooms();
        if(roomsList.isEmpty()){
            roomResponse.setDescription("No Rooms found");

        }else{
            for(Room room : roomsList){
                byte[] photoBytes = roomService.getRoomPhotoById(room.getId());
                if(photoBytes != null || photoBytes.length > 0){
                    String base64Photo = Base64.getEncoder().encode(photoBytes).toString();
                    //RoomResponse roomResponse1 = getRoomResponse(room);
                    roomResponse.setId(room.getId());
                    roomResponse.setRoomType(room.getRoomType());
                    roomResponse.setRoomPrice(room.getRoomPrice());
                    roomResponse.setPhoto(base64Photo);
                    roomListResponse.add(roomResponse);
                }

            }

        }

        return ResponseEntity.ok(roomListResponse);
    }

    @GetMapping("/room/id")
    public ResponseEntity<RoomResponse> getRoomById(@RequestParam("id") Long id){
        RoomResponse roomResponse = new RoomResponse();
        Room room = roomService.getRoomById(id);
        if(room.getId().equals(id)){
            roomResponse.setDescription("Room found : " + room.getRoomType());
        }else{
            roomResponse.setDescription("Room not found");
        }
        return ResponseEntity.ok(roomResponse);
    }

    @PostMapping("/delete/room")
    public ResponseEntity<RoomResponse> deleteRoom(@RequestParam("id") Long id){
        RoomResponse roomResponse = new RoomResponse();
        Room room = roomService.getRoomById(id);
        if(room.getId().equals(id)){
            //delete room
            roomService.deleteRoom(id);
            roomResponse.setDescription("Room deleted : " + room.getRoomType());
        }else{
            roomResponse.setDescription("Room not found");
        }
        return ResponseEntity.ok(roomResponse);
    }

    @PostMapping("/room/edit")
    public ResponseEntity<RoomResponse> updateRoomDetails(@RequestParam("id") Long id,
                                                          @RequestParam("file") MultipartFile file,
                                                          @RequestParam("roomType") String roomType,
                                                          @RequestParam("roomPrice") BigDecimal roomPrice) throws Exception {
        RoomResponse roomResponse = new RoomResponse();
        Room updatedRoom = new Room();
        Room existingRoom = roomService.getRoomById(id);
        if(existingRoom.getId().equals(id)){
            //update Room details
            updatedRoom = roomService.updateRoom(id, file, roomType, roomPrice);
            roomResponse= new RoomResponse(updatedRoom.getId(), updatedRoom.getRoomType(), updatedRoom.getRoomPrice());
        }else{
            roomResponse.setDescription("Room with given id is not found");
        }
        return ResponseEntity.ok(roomResponse);
    }
}
