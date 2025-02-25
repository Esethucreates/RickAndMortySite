package com.esethucreates.RickAndMortySite.DTO;

import java.util.List;

public record ResultsItem(
	String image,
	String gender,
	String species,
	Origin origin,
	String name,
	Location location,
	List<String> episode,
	int id,
	String type,
	String url,
	String status
) {
}