package com.example.managerservice.service.Implementation;

import com.example.managerservice.model.Country;
import com.example.managerservice.service.Interfaces.ICountryService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CountryService implements ICountryService {
    private final WebClient webClient;

    public CountryService(WebClient databaseWebClient) {
        this.webClient = databaseWebClient;
    }

    @Override
    public List<Country> getAll() {
        List<Country> countryList = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/country/getAllCountries")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Country>>() {
                })
                .block();
        return countryList;
    }

    @Override
    public Country save(Country country) {
        Country savedCountry = webClient.post()
                .uri("/country/save")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(country), Country.class)
                .retrieve()
                .bodyToMono(Country.class)
                .block();
        return savedCountry;
    }

    @Override
    public Country getCountryById(long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/country/getCountryById")
                        .queryParam("countryId", id)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Country.class)
                .block();
    }
}
