package com.esethucreates.RickAndMortySite.service;

import com.esethucreates.RickAndMortySite.DTO.Info;
import com.esethucreates.RickAndMortySite.DTO.ResultsItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


// This class is to mutate method in RestAPIClient. It doesn't create, it mutates
public interface RestAPIService {
    Flux<ResultsItem> returnResultsFromResponse();
    Mono<Info> returnInfoResultsFromResponse();
}
