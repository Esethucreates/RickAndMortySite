package com.esethucreates.RickAndMortySite.controllers;

import com.esethucreates.RickAndMortySite.DTO.response.Info;
import com.esethucreates.RickAndMortySite.DTO.character.CharacterResponse;
import com.esethucreates.RickAndMortySite.service.receiverService.RestAPIService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@RestController
@RequestMapping("/api/characters")
public class CharacterController implements ControllerInterface<CharacterResponse>{

    private final RestAPIService<CharacterResponse> apiService;
    public CharacterController(RestAPIService<CharacterResponse> apiService) {
        this.apiService = apiService;
    }

    @GetMapping("")
    public Mono<Map<String, Object>> findAll(@RequestParam(name = "page", required = false) Integer page){
        Integer requestedPage = Optional.ofNullable(page).orElse(1);

        Mono<List<CharacterResponse>> characterListMono = apiService.returnResultsFromResponse(requestedPage);
        Mono<Info> infoMono = apiService.returnInfoResultsFromResponse(requestedPage);

        return Mono.zip(characterListMono, infoMono)
                .map(tuple -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("characterList", tuple.getT1());
                    response.put("info", tuple.getT2());
                    return response;
                });
    }

    @GetMapping("/{charId}")
    public Mono<CharacterResponse> findById(@PathVariable Integer charId) {
        return apiService.returnSingleResultItem(charId);
    }
}
