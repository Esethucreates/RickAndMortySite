package com.esethucreates.RickAndMortySite.controllers;

import com.esethucreates.RickAndMortySite.DTO.character.Info;
import com.esethucreates.RickAndMortySite.DTO.character.ResultsItem;
import com.esethucreates.RickAndMortySite.service.receiverService.RestAPIService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;


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
@RequestMapping("/characters")
public class CharacterController {

    private final RestAPIService<ResultsItem> apiService;

    public CharacterController(RestAPIService<ResultsItem> apiService) {
        this.apiService = apiService;
    }

    @GetMapping("")
    public Mono<String> homePage(@RequestParam(name = "page", required = false) Integer page, Model model) {
        Integer requestedPage = Optional.ofNullable(page).orElse(0);

        Mono<List<ResultsItem>> characterListMono = apiService.returnResultsFromResponse(requestedPage);
        Mono<Info> infoMono = apiService.returnInfoResultsFromResponse(requestedPage);

        return Mono.zip(characterListMono, infoMono)
                .doOnNext(tuple -> {
                    model.addAttribute("characterList", tuple.getT1());
                    model.addAttribute("info", tuple.getT2());
                })
                .thenReturn("home-page");
    }

    @GetMapping("/{charId}")
    public Mono<String> getCharacterDetails(@PathVariable Integer charId, Model model) {
        return apiService.returnSingleResultItem(charId)
                .doOnNext(resultsItem -> model.addAttribute("charInfo", resultsItem))
                .thenReturn("Character-Page");

    }
}
