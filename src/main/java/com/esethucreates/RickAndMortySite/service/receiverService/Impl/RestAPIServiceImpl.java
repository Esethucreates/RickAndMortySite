package com.esethucreates.RickAndMortySite.service.receiverService.Impl;


import com.esethucreates.RickAndMortySite.DTO.character.Info;
import com.esethucreates.RickAndMortySite.DTO.character.Response;
import com.esethucreates.RickAndMortySite.DTO.character.ResultsItem;
import com.esethucreates.RickAndMortySite.DTO.location.LocationResponse;
import com.esethucreates.RickAndMortySite.service.client.Impl.CharacterEndpointClient;
import com.esethucreates.RickAndMortySite.service.client.Impl.LocationEndpointClient;
import com.esethucreates.RickAndMortySite.service.receiverService.RestAPIService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class RestAPIServiceImpl implements RestAPIService {

    private final CharacterEndpointClient characterClient;
    private final LocationEndpointClient locationClient;


    public RestAPIServiceImpl(CharacterEndpointClient characterClient, LocationEndpointClient locationClient) {
        this.characterClient = characterClient;
        this.locationClient = locationClient;
    }


//    Return the flux resultItems
    @Override
    public Flux<ResultsItem> returnResultsFromResponse(Integer page) {

        return characterClient.returnPageResponse(page)
                .map(Response::results)
                .flatMapMany(Flux::fromIterable); // Doesn't change its signature, just makes it many
    }

    //    Return the Mono info
    @Override
    public Mono<Info> returnInfoResultsFromResponse(Integer page) {
        return characterClient.returnPageResponse(page)
                .map(Response::info);
    }

    @Override
    public Mono<ResultsItem> returnResultItemForCharacter(Integer charId) {
        return characterClient.returnPageResultsObject(charId);
    }

    @Override
    public Flux<LocationResponse> returnLocationFromResponse(Integer page) {
        return locationClient.returnPageResponse(page)
                .map(Response::results)
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<List<LocationResponse>> returnLocationResultsFromResponse(Integer page) {
        return locationClient.returnPageResponse(page)
                .map(Response::results)
                .flatMapMany(Flux::fromIterable)
                .collectList();
    }

}
