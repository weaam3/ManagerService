package com.example.managerservice.service.Interfaces;



import com.example.managerservice.model.RoomType;

import java.util.List;

public interface IRoomTypeService {
    List<RoomType> getAllRoomTypes();

    List<RoomType> getAllRoomTypesByHotelID(long hotelId);

    RoomType getRoomTypeById(long roomTypeId);
}
