package com.example.managerservice.service.Interfaces;


import com.example.managerservice.model.Room;

import java.util.List;

public interface IRoomService {
    Room save(Room room);
    Room getById(long roomId);

    List<Room> getAllByHotelIdAndManagerId(long hotelId, long roomId);

    Room updateRoom(Room room);

    Room deleteById(long roomId);
}
