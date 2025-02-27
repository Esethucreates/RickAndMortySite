package com.esethucreates.RickAndMortySite.DTO.response;

import java.util.List;

public record Response<T>(
	List<T> results,
	Info info
) {
}