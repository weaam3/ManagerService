package com.example.managerservice.service.Implementation;

import com.example.managerservice.model.Role;
import com.example.managerservice.service.Interfaces.IRoleService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RoleService implements IRoleService {
    private final WebClient databaseWebClient;
    public RoleService(WebClient databaseWebClient) {
        this.databaseWebClient = databaseWebClient;
    }
    @Override
    public List<Role> getAll(){
        List<Role> rolesList = databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/role/getAll")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Role>>(){})
                .block();
        return rolesList;
    }
    @Override
    public Role getById(long roleId){
        Role role = databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/role/getById")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Role.class)
                .block();
        return role;
    }
    @Override
    public Role save(Role role){
        Role savedRole = databaseWebClient.post()
                .uri("/role/save")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(role), Role.class)
                .retrieve()
                .bodyToMono(Role.class)
                .block();
        return savedRole;
    }
}
