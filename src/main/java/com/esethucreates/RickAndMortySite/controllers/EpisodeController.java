package com.esethucreates.RickAndMortySite.controllers;

import com.esethucreates.RickAndMortySite.DTO.episode.EpisodeResponse;
import com.esethucreates.RickAndMortySite.DTO.response.Info;
import com.esethucreates.RickAndMortySite.service.receiverService.Impl.EpisodeAPIService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/episodes")
public class EpisodeController implements ControllerInterface<EpisodeResponse>{
    private final EpisodeAPIService apiService;


    public EpisodeController(EpisodeAPIService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("")
    @Override
    public Mono<Map<String, Object>> findAll(@RequestParam(name = "page", required = false) Integer page) {
        Integer requestedPage = Optional.ofNullable(page).orElse(0);

        // Service
        Mono<List<EpisodeResponse>> episodeList = apiService.returnResultsFromResponse(page);
        Mono<Info> info= apiService.returnInfoResultsFromResponse(page);

       return Mono.zip(episodeList, info)
                .map(objects -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("episodeList", objects.getT1());
                    response.put("info", objects.getT2());
                    return response;
                        });
    }

    @GetMapping("/{id}")
    @Override
    public Mono<EpisodeResponse> findById(@PathVariable Integer id) {
        return apiService.returnSingleResultItem(id);
    }
}
