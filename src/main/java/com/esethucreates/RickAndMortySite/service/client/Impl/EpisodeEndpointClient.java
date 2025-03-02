package com.esethucreates.RickAndMortySite.service.client.Impl;

import com.esethucreates.RickAndMortySite.DTO.episode.EpisodeResponse;
import com.esethucreates.RickAndMortySite.DTO.response.Response;
import com.esethucreates.RickAndMortySite.service.client.RetrieveCharacterObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class EpisodeEndpointClient implements RetrieveCharacterObject<Response<EpisodeResponse>, EpisodeResponse> {

    private final WebClient webClient;

    public EpisodeEndpointClient(WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public Mono<Response<EpisodeResponse>> returnPageResponse(Integer page) {
        final ParameterizedTypeReference<Response<EpisodeResponse>> pageResponse =
                new ParameterizedTypeReference<Response<EpisodeResponse>>() {};
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/episode").queryParam("page", page).build())
                .retrieve().bodyToMono(pageResponse);
    }

    @Override
    public Mono<EpisodeResponse> returnPageResultsObject(Integer id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/episode/{id}").build(id))
                .retrieve().bodyToMono(EpisodeResponse.class);
    }

    @Override
    public Flux<EpisodeResponse> getCharacterListById(List<Integer> listId) {
        return null;
    }
}
