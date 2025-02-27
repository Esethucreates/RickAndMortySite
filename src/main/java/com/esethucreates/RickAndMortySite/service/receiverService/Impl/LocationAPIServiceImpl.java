package com.esethucreates.RickAndMortySite.service.receiverService.Impl;

import com.esethucreates.RickAndMortySite.DTO.response.Info;
import com.esethucreates.RickAndMortySite.DTO.response.Response;
import com.esethucreates.RickAndMortySite.DTO.location.LocationResponse;
import com.esethucreates.RickAndMortySite.service.client.Impl.LocationEndpointClient;
import com.esethucreates.RickAndMortySite.service.receiverService.RestAPIService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class LocationAPIServiceImpl implements RestAPIService<LocationResponse> {
    private final LocationEndpointClient locationEndpointClient;

    public LocationAPIServiceImpl(LocationEndpointClient locationEndpointClient) {
        this.locationEndpointClient = locationEndpointClient;
    }

    @Override
    public Mono<List<LocationResponse>> returnResultsFromResponse(Integer page) {
        return locationEndpointClient.returnPageResponse(page)
                .map(Response::results)
                .flatMapMany(Flux::fromIterable)
                .collectList();
    }

    @Override
    public Mono<LocationResponse> returnSingleResultItem(Integer charId) {
        return locationEndpointClient.returnPageResultsObject(charId);
    }

    @Override
    public Mono<Info> returnInfoResultsFromResponse(Integer page) {
        return locationEndpointClient.returnPageResponse(page)
                .map(Response::info);
    }
}
