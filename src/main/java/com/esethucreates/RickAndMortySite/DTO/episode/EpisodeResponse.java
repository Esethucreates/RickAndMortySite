package com.esethucreates.RickAndMortySite.DTO.episode;

import java.util.List;

public record EpisodeResponse(
	String airDate,
	List<String> characters,
	String name,
	String episode,
	int id,
	String url
) {
}