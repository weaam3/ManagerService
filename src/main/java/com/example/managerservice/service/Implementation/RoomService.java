package com.example.managerservice.service.Implementation;

import com.example.managerservice.model.Room;
import com.example.managerservice.service.Interfaces.IRoomService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RoomService implements IRoomService {
    private final WebClient webClient;

    public RoomService(WebClient databaseWebClient) {
        this.webClient = databaseWebClient;
    }

    @Override
    public Room save(Room room) {
        Room savedRoom = webClient.post()
                .uri("/room/save")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(room), Room.class)
                .retrieve()
                .bodyToMono(Room.class)
                .block();
        return savedRoom;
    }

    @Override
    public Room getById(long roomId) {
        Room room = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/room/getById")
                        .queryParam("roomId", roomId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Room.class)
                .block();
        return room;
    }

    @Override
    public List<Room> getAllByHotelIdAndManagerId(long hotelId, long roomId) {
        List<Room> rooms = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/room/getAllByHotelIdAndManagerId")
                        .queryParam("hotelId", hotelId)
                        .queryParam("roomId", roomId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Room>>() {
                })
                .block();
        return rooms;
    }

    @Override
    public Room updateRoom(Room room) {
        return webClient.post()
                .uri("/room/update")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(room), Room.class)
                .retrieve()
                .bodyToMono(Room.class)
                .block();
    }

    @Override
    public Room deleteById(long roomId) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/room/delete")
                        .queryParam("roomId", roomId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Room.class)
                .block();
    }
}
