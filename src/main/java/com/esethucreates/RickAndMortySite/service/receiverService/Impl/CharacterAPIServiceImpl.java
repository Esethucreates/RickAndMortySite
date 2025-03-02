package com.esethucreates.RickAndMortySite.service.receiverService.Impl;


import com.esethucreates.RickAndMortySite.DTO.response.Info;
import com.esethucreates.RickAndMortySite.DTO.response.Response;
import com.esethucreates.RickAndMortySite.DTO.character.CharacterResponse;
import com.esethucreates.RickAndMortySite.service.client.Impl.CharacterEndpointClient;
import com.esethucreates.RickAndMortySite.service.receiverService.RestAPIService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class CharacterAPIServiceImpl implements RestAPIService<CharacterResponse> {

    private final CharacterEndpointClient characterClient;


    public CharacterAPIServiceImpl(CharacterEndpointClient characterClient) {
        this.characterClient = characterClient;
    }


    //    Return the flux resultItems
    @Override
    public Mono<List<CharacterResponse>> returnResultsFromResponse(Integer page) {

        return characterClient.returnPageResponse(page)
                .map(Response::results)
                .flatMapMany(Flux::fromIterable) // Doesn't change its signature, just makes it many
                .collectList();
    }

    //    Return the Mono info
    @Override
    public Mono<Info> returnInfoResultsFromResponse(Integer page) {
        return characterClient.returnPageResponse(page)
                .map(Response::info);
    }

    @Override
    public Mono<CharacterResponse> returnSingleResultItem(Integer charId) {
        return characterClient.returnPageResultsObject(charId);
    }

    @Override
    public Mono<List<CharacterResponse>> returnListOfCharacters(List<Integer> ListIds) {
        return characterClient.getCharacterListById(ListIds)
                .collectList();
    }


}
