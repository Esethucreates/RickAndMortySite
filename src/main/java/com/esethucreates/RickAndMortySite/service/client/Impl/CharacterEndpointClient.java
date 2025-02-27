package com.esethucreates.RickAndMortySite.service.client.Impl;

import com.esethucreates.RickAndMortySite.DTO.response.Response;
import com.esethucreates.RickAndMortySite.DTO.character.CharacterResponse;
import com.esethucreates.RickAndMortySite.service.client.RetrieveCharacterObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class CharacterEndpointClient implements RetrieveCharacterObject<Response<CharacterResponse>, CharacterResponse> {
    private final WebClient webClient;

    public CharacterEndpointClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Response<CharacterResponse>> returnPageResponse(Integer page) {
        final ParameterizedTypeReference<Response<CharacterResponse>> resultItemResponsePage =
                new ParameterizedTypeReference<Response<CharacterResponse>>() {};


        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/character").queryParam("page", page).build())
                .retrieve().bodyToMono(resultItemResponsePage);
    }

    @Override
    public Mono<CharacterResponse> returnPageResultsObject(Integer id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/character/{charId}").build(id))
                .retrieve()
                .bodyToMono(CharacterResponse.class);
    }


}

