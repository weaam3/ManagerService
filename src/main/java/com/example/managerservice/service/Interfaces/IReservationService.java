package com.example.managerservice.service.Interfaces;


import com.example.managerservice.model.Reservation;

import java.util.List;

public interface IReservationService {
    Reservation save(Reservation reservation, Long managerId);
    List<Reservation> getAllByHotelAndManagerId(Long hotelId, Long managerId);
    Reservation getByReservationIdAndManagerId(Long reservationId, Long managerId);

    List<Reservation> getAllByManagerId(long managerId);

    Reservation update(Reservation reservation);

    Reservation delete(long reservationId);
}
