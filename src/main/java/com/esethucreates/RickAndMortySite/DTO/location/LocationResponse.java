package com.esethucreates.RickAndMortySite.DTO.location;

import java.util.List;

public record LocationResponse(
	String name,
	List<String> residents,
	int id,
	String type,
	String dimension,
	String url
) {
}