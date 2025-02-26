package com.esethucreates.RickAndMortySite.DTO.character;

import java.util.List;

public record Response<T>(
	List<T> results,
	Info info
) {
}