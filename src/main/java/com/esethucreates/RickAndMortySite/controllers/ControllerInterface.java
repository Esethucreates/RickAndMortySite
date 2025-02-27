package com.esethucreates.RickAndMortySite.controllers;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface ControllerInterface<T> {
    Mono<Map<String, Object>> findAll( Integer page);
    Mono<T> findById( Integer id);
}
