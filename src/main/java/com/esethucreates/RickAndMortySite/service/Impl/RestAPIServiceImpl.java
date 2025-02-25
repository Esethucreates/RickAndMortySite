package com.esethucreates.RickAndMortySite.service.Impl;


import com.esethucreates.RickAndMortySite.DTO.Info;
import com.esethucreates.RickAndMortySite.DTO.Response;
import com.esethucreates.RickAndMortySite.DTO.ResultsItem;
import com.esethucreates.RickAndMortySite.service.RestAPIService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class RestAPIServiceImpl implements RestAPIService {

    private final RestAPIClientServiceImpl clientService;

    public RestAPIServiceImpl(RestAPIClientServiceImpl clientService) {
        this.clientService = clientService;
    }


//    Return the flux resultItems
    @Override
    public Flux<ResultsItem> returnResultsFromResponse() {
        return clientService.returnPageResponse()
                .map(Response::results)
                .flatMapMany(Flux::fromIterable); // Doesn't change its signature, just makes it many
    }

    //    Return the Mono info

    // Come back to fix this mono and subscriber
    @Override
    public Mono<Info> returnInfoResultsFromResponse() {
        return clientService.returnPageResponse()
                .map(Response::info);
    }



}
