package com.esethucreates.RickAndMortySite.service.receiverService.Impl;

import com.esethucreates.RickAndMortySite.DTO.episode.EpisodeResponse;
import com.esethucreates.RickAndMortySite.DTO.response.Info;
import com.esethucreates.RickAndMortySite.DTO.response.Response;
import com.esethucreates.RickAndMortySite.service.client.Impl.EpisodeEndpointClient;
import com.esethucreates.RickAndMortySite.service.receiverService.RestAPIService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
@Service
public class EpisodeAPIService implements RestAPIService<EpisodeResponse> {

    private final EpisodeEndpointClient episodeEndpointClient;

    public EpisodeAPIService(EpisodeEndpointClient episodeEndpointClient) {
        this.episodeEndpointClient = episodeEndpointClient;
    }


    @Override
    public Mono<Info> returnInfoResultsFromResponse(Integer page) {
        return episodeEndpointClient.returnPageResponse(page)
                .map(Response::info);
    }

    @Override
    public Mono<List<EpisodeResponse>> returnResultsFromResponse(Integer page) {

        return episodeEndpointClient.returnPageResponse(page)
                .map(Response::results)
                .flatMapMany(Flux::fromIterable)
                .collectList();
    }

    @Override
    public Mono<EpisodeResponse> returnSingleResultItem(Integer charId) {
        return episodeEndpointClient.returnPageResultsObject(charId);
    }
}
