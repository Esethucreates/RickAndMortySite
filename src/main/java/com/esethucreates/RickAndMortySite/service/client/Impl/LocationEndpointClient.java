package com.esethucreates.RickAndMortySite.service.client.Impl;

import com.esethucreates.RickAndMortySite.DTO.character.Response;
import com.esethucreates.RickAndMortySite.DTO.location.LocationResponse;
import com.esethucreates.RickAndMortySite.service.client.RetrieveCharacterObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class LocationEndpointClient implements RetrieveCharacterObject<Response<LocationResponse>, LocationResponse> {
    private final WebClient webClient;
    public LocationEndpointClient(WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public Mono<Response<LocationResponse>> returnPageResponse(Integer page) {
        final ParameterizedTypeReference<Response<LocationResponse>> locationResponsePage =
                new ParameterizedTypeReference<Response<LocationResponse>>() {};

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/location").queryParam("page", page).build())
                .retrieve()
                .bodyToMono(locationResponsePage);
    }


    @Override
    public Mono<LocationResponse> returnPageResultsObject(Integer id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/location/{id}").build(id))
                .retrieve()
                .bodyToMono(LocationResponse.class);
    }
}
