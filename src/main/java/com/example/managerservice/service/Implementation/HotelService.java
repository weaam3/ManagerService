package com.example.managerservice.service.Implementation;


import com.example.managerservice.model.Country;
import com.example.managerservice.model.DBHotel;
import com.example.managerservice.model.Hotel;
import com.example.managerservice.service.Interfaces.ICountryService;
import com.example.managerservice.service.Interfaces.IHotelService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService implements IHotelService {
    private final WebClient webClient;
    private final ICountryService countryService;

    public HotelService(WebClient databaseWebClient, ICountryService countryService) {
        this.webClient = databaseWebClient;
        this.countryService = countryService;
    }

    @Override
    public Hotel save(Hotel hotel) {
        DBHotel savedHotel = webClient.post()
                .uri("/hotel/save")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(toDBHotel(hotel)), DBHotel.class)
                .retrieve()
                .bodyToMono(DBHotel.class)
                .block();
        return fromDBHotel(savedHotel);
    }

    @Override
    public List<Hotel> getAllByManagerID(Long managerId) {
        List<DBHotel> hotelsList = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/hotel/allByManagerID")
                        .queryParam("managerId", managerId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<DBHotel>>() {
                })
                .block();
        List<Hotel> hotels = new ArrayList<>();
        if (hotelsList != null)
            for (DBHotel dbHotel : hotelsList) {
                hotels.add(fromDBHotel(dbHotel));
            }
        return hotels;
    }

    @Override
    public Hotel getByHotelIdAndManagerId(Long hotelId, Long managerId) {
        DBHotel dbHotel = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/hotel/byHotelIdAndManagerId")
                        .queryParam("hotelId", hotelId)
                        .queryParam("managerId", managerId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DBHotel.class)
                .block();
        return fromDBHotel(dbHotel);
    }

    @Override
    public Hotel update(Hotel hotel) {
        DBHotel updatedHotel = webClient.put()
                .uri("/hotel/update")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(toDBHotel(hotel)), DBHotel.class)
                .retrieve()
                .bodyToMono(DBHotel.class)
                .block();
        return fromDBHotel(updatedHotel);
    }

    @Override
    public Hotel deleteByIdAndManagerID(Long hotelId, Long managerId) {
        DBHotel deletedHotel = webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/hotel/delete")
                        .queryParam("hotelId", hotelId)
                        .queryParam("managerId", managerId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DBHotel.class)
                .block();
        return fromDBHotel(deletedHotel);
    }

    private Hotel fromDBHotel(DBHotel dbHotel) {
        if (dbHotel == null)
            return null;
        Country country = countryService.getCountryById(dbHotel.getCountryId());
        return new Hotel(dbHotel.getId(), dbHotel.getName(), dbHotel.getManagerId(), dbHotel.getCountryId(), country != null ? country.getName() : "");
    }

    private DBHotel toDBHotel(Hotel hotel) {
        return hotel == null ? null : new DBHotel(hotel.getId(), hotel.getName(), hotel.getManagerId(), hotel.getCountryId());
    }
}
