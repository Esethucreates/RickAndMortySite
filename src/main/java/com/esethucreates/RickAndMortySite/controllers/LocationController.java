package com.esethucreates.RickAndMortySite.controllers;


import com.esethucreates.RickAndMortySite.DTO.location.LocationResponse;
import com.esethucreates.RickAndMortySite.DTO.response.Info;
import com.esethucreates.RickAndMortySite.service.receiverService.Impl.LocationAPIServiceImpl;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController implements ControllerInterface<LocationResponse> {
    private final LocationAPIServiceImpl apiService;


    public LocationController(LocationAPIServiceImpl apiService) {
        this.apiService = apiService;
    }


    @GetMapping("")
    public Mono<Map<String, Object>> findAll(@RequestParam(name = "page", required = false) Integer page) {
        Integer requestedPage = Optional.ofNullable(page).orElse(1);

        Mono<List<LocationResponse>> locationResponse = apiService.returnResultsFromResponse(requestedPage);
        Mono<Info> infoMono = apiService.returnInfoResultsFromResponse(requestedPage);

        return Mono.zip(locationResponse, infoMono)
                .map(objects -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("locationList", objects.getT1());
                    response.put("info", objects.getT2());
                    return response;
                });
    }

    @GetMapping("/{locId}")
    public Mono<LocationResponse> findById(@PathVariable Integer locId) {
        return apiService.returnSingleResultItem(locId);
    }
}
