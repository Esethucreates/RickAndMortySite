package com.esethucreates.RickAndMortySite.DTO.character;

import java.util.List;

public record CharacterResponse(
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