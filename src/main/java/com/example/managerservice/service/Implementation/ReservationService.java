package com.example.managerservice.service.Implementation;

import com.example.managerservice.model.Reservation;
import com.example.managerservice.service.Interfaces.IReservationService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ReservationService implements IReservationService {
    private final WebClient databaseWebClient;

    public ReservationService(WebClient databaseWebClient) {
        this.databaseWebClient = databaseWebClient;
    }

    @Override
    public Reservation save(Reservation reservation, Long managerId) {
        Reservation SavedReservation = databaseWebClient.post()
                .uri("/Reservation/save")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(reservation), Reservation.class)
                .retrieve()
                .bodyToMono(Reservation.class)
                .block();
        return SavedReservation;
    }

    @Override
    public List<Reservation> getAllByHotelAndManagerId(Long hotelId, Long managerId) {
        List<Reservation> ReservationList = databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/Reservation/byHotelIdAndManagerID")
                        .queryParam("hotelId", hotelId)
                        .queryParam("managerId", managerId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Reservation>>() {
                })
                .block();
        return ReservationList;
    }

    @Override
    public Reservation getByReservationIdAndManagerId(Long reservationId, Long managerId) {
        Reservation reservation = databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/Reservation/reservationIdAndManagerId")
                        .queryParam("reservationId", reservationId)
                        .queryParam("managerId", managerId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Reservation.class)
                .block();
        return reservation;
    }

    @Override
    public List<Reservation> getAllByManagerId(long managerId) {
        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/Reservation/byManagerID")
                        .queryParam("managerId", managerId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Reservation>>() {
                })
                .block();
    }

    @Override
    public Reservation update(Reservation reservation) {
        return databaseWebClient.put()
                .uri("/Reservation/update")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(reservation), Reservation.class)
                .retrieve()
                .bodyToMono(Reservation.class)
                .block();
    }

    @Override
    public Reservation delete(long reservationId) {
        return databaseWebClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/Reservation/delete")
                        .queryParam("reservationId", reservationId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Reservation.class)
                .block();
    }
}
