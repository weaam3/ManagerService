package com.example.managerservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private long id;
    private long userId;
    private long roomId;
    private double cost;
    private Timestamp dateFrom;
    private Timestamp dateTo;
}
