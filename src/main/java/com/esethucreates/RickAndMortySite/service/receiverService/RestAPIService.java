package com.esethucreates.RickAndMortySite.service.receiverService;

import com.esethucreates.RickAndMortySite.DTO.response.Info;
import reactor.core.publisher.Mono;

import java.util.List;


// This class is to mutate method in RestAPIClient. It doesn't create, it mutates
public interface RestAPIService<RO> {
    //    Common object result
    Mono<Info> returnInfoResultsFromResponse(Integer page);

    Mono<List<RO>> returnResultsFromResponse(Integer page);

    Mono<RO> returnSingleResultItem(Integer charId);


}
