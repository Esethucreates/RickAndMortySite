package com.esethucreates.RickAndMortySite.controllers;

import com.esethucreates.RickAndMortySite.DTO.character.CharacterResponse;
import com.esethucreates.RickAndMortySite.service.receiverService.Impl.CharacterAPIServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HomePageController {
//    Home page must have a featured items. Can range from
    private final CharacterAPIServiceImpl apiService;


    public HomePageController(CharacterAPIServiceImpl apiService) {
        this.apiService = apiService;
    }

    @GetMapping("")
    public Mono<List<CharacterResponse>> getCharacterIds(){
        return apiService.returnListOfCharacters(returnListWithRandomNums());
    }

    private List<Integer> returnListWithRandomNums(){
        List<Integer> randomIds = new ArrayList<>();
        for (int i = 0; i < 6; i++ ){
            int id = (int) (Math.random() * 827);
            randomIds.add(id);
        }
        return randomIds;
    }
}
