package com.esethucreates.RickAndMortySite.service;

import com.esethucreates.RickAndMortySite.DTO.Response;
import reactor.core.publisher.Mono;


// Only use this class to retrieve information from the Rest API.
// Do not modify the info, just retrieve it
public interface RestAPIClientService {


//    Get All characters available.
    Mono<Response> returnPageResponse();
}
