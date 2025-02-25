package com.esethucreates.RickAndMortySite.DTO;

import java.util.List;

public record Response(
	List<ResultsItem> results,
	Info info
) {
}