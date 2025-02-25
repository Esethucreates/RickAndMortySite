package com.esethucreates.RickAndMortySite.service.Impl;

import com.esethucreates.RickAndMortySite.DTO.Response;
import com.esethucreates.RickAndMortySite.service.RestAPIClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class RestAPIClientServiceImpl implements RestAPIClientService {
    private final WebClient webClient;

    public RestAPIClientServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Response> returnPageResponse() {
        return webClient.get()
                .uri("/character")
                .retrieve().bodyToMono(Response.class);
    }
}
