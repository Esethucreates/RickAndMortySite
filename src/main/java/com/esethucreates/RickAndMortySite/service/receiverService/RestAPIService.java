package com.esethucreates.RickAndMortySite.service.receiverService;

import com.esethucreates.RickAndMortySite.DTO.character.Info;
import com.esethucreates.RickAndMortySite.DTO.character.ResultsItem;
import com.esethucreates.RickAndMortySite.DTO.location.LocationResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


// This class is to mutate method in RestAPIClient. It doesn't create, it mutates
public interface RestAPIService {
    Flux<ResultsItem> returnResultsFromResponse(Integer page);
    Mono<Info> returnInfoResultsFromResponse(Integer page);
    Mono<ResultsItem> returnResultItemForCharacter(Integer charId);
    Flux<LocationResponse> returnLocationFromResponse(Integer page);
    Mono<List<LocationResponse>> returnLocationResultsFromResponse(Integer page);

}
