package com.example.managerservice.service.Implementation;


import com.example.managerservice.model.User;
import com.example.managerservice.service.Interfaces.IUserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService implements IUserService {

    private final WebClient authController;
    private final WebClient databaseWebClient;

    public UserService(WebClient authWebClient, WebClient databaseWebClient) {
        this.authController = authWebClient;
        this.databaseWebClient = databaseWebClient;
    }

    @Override
    public User save(User user) {
        return authController.post()
                .uri("/user/signUp")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    @Override
    public User getByUserName(String username, String password) {
        return authController.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/signIn")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new User(0, username, password, "", 0)), User.class)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    @Override
    public User getUsernameById(long userId) {
        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/getUserByUserId")
                        .queryParam("userId", userId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }
}
