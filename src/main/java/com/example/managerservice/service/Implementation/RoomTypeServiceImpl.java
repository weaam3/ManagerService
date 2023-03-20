package com.example.managerservice.service.Implementation;


import com.example.managerservice.model.RoomType;
import com.example.managerservice.service.Interfaces.IRoomTypeService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class RoomTypeServiceImpl implements IRoomTypeService {
    private final WebClient databaseWebClient;

    public RoomTypeServiceImpl(WebClient databaseWebClient) {
        this.databaseWebClient = databaseWebClient;
    }

    @Override
    public List<RoomType> getAllRoomTypes() {
        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/roomType/getAllRoomTypes")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RoomType>>() {
                })
                .block();
    }

    @Override
    public List<RoomType> getAllRoomTypesByHotelID(long hotelId) {
        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/roomType/getAllRoomTypesByHotelID")
                        .queryParam("hotelId", hotelId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RoomType>>() {
                })
                .block();
    }

    @Override
    public RoomType getRoomTypeById(long roomTypeId) {
        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/roomType/getRoomTypeById")
                        .queryParam("roomTypeId", roomTypeId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(RoomType.class)
                .block();
    }
}
