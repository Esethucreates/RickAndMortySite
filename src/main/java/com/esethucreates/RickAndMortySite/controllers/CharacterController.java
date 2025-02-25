package com.esethucreates.RickAndMortySite.controllers;

import com.esethucreates.RickAndMortySite.DTO.Info;
import com.esethucreates.RickAndMortySite.DTO.ResultsItem;
import com.esethucreates.RickAndMortySite.service.RestAPIService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


/*
 *
 * When you use RestController, the returned value (e.g., "index") is interpreted as the response body, not a view name.
 * This means the string "index" is sent directly to the HTTP response as plain text, bypassing the Thymeleaf view resolver.
 *  For Thymeleaf to process the index.html template, the view name (like "index") needs to be passed to the view resolver,
 *  which only happens when you use Controller
 *
 *  */

// All you had to do was change RestController to Controller due to the above reason
@Controller
@RequestMapping("/api/characters")
public class CharacterController {

    private final RestAPIService apiService;

    public CharacterController(RestAPIService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/all")
    public Mono<String> homePage2(Model model) {
        Mono<List<ResultsItem>> characterListMono = apiService.returnResultsFromResponse().collectList();
        Mono<Info> infoMono = apiService.returnInfoResultsFromResponse();

        return Mono.zip(characterListMono, infoMono)
                .doOnNext(tuple -> {
                    model.addAttribute("characterList", tuple.getT1());
                    model.addAttribute("info", tuple.getT2());
                })
                .thenReturn("home-page");
    }

    //    Get all the characters in that page
//    TODO: Make a zip return a String to be rendered
    @GetMapping("/NotUsingThisOneYet")
    public String homePage(Model model) {
        // Get data from the services and Set the info
        Flux<ResultsItem> characterListMono = apiService.returnResultsFromResponse();
//        Mono<Info> infoMono = apiService.returnInfoResultsFromResponse();

//        Convert infoMono to a flux
        Flux<Info> fluxConvertedMonoInfo = apiService.returnInfoResultsFromResponse().flatMapMany(Flux::just);
//        Zip the two together and use ReactiveDataDriver to set them as data drivers
        Flux.zip(characterListMono, fluxConvertedMonoInfo)
                .doOnNext(tuple2 -> {
                    model.addAttribute("characterList", new ReactiveDataDriverContextVariable(tuple2.getT1()));
                    model.addAttribute("info", new ReactiveDataDriverContextVariable(tuple2.getT2()));
                }).subscribe();

        return "home-page";
    }


}


















