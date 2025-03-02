package com.esethucreates.RickAndMortySite.DTO.episode;

import java.util.List;

public record EpisodeResponse(
	String air_date,
	List<String> characters,
	String name,
	String episode,
	int id,
	String url
) {
}