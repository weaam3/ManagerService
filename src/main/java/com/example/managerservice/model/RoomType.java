package com.example.managerservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RoomType {
    private long id;
    private String name;
    private double price;
    private long hotelId;

}