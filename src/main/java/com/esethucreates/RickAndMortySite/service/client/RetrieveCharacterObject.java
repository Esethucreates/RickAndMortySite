package com.esethucreates.RickAndMortySite.service.client;

import reactor.core.publisher.Mono;


// Only use this class to retrieve information from the Rest API.
// Do not modify the info, just retrieve it
public interface RetrieveCharacterObject<T, O>{



// Get page Response
    Mono<T> returnPageResponse(Integer page);

//    Get single item response by ID
    Mono<O> returnPageResultsObject(Integer id);


}
