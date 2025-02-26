package com.esethucreates.RickAndMortySite.controllers;


import com.esethucreates.RickAndMortySite.DTO.character.Info;
import com.esethucreates.RickAndMortySite.DTO.location.LocationResponse;
import com.esethucreates.RickAndMortySite.service.receiverService.Impl.LocationAPIServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/locations")
public class LocationController {
    private final LocationAPIServiceImpl locationAPIService;


    public LocationController(LocationAPIServiceImpl locationAPIService) {
        this.locationAPIService = locationAPIService;
    }


    @GetMapping("")
    public Mono<String> getALlLocation(
            @RequestParam(name = "page", required = false) Integer page, Model model
    ){
        Integer requestedPage = Optional.ofNullable(page).orElse(0);
        Mono<List<LocationResponse>> locationResponse = locationAPIService.returnResultsFromResponse(requestedPage);
        Mono<Info> infoMono = locationAPIService.returnInfoResultsFromResponse(requestedPage);


        return Mono.zip(locationResponse, infoMono)
                .doOnNext( objects -> {
                    model.addAttribute("locationList", objects.getT1());
                    model.addAttribute("info", objects.getT2());
                }).thenReturn("location-home");

    }

    @GetMapping("/{locId}")
    public Mono<String> getSingleLocation(@PathVariable Integer locId, Model model){
        return locationAPIService.returnSingleResultItem(locId)
                .doOnNext(locationResponse -> model.addAttribute("locationInfo", locationResponse))
                .thenReturn("location-page");
    }
}
