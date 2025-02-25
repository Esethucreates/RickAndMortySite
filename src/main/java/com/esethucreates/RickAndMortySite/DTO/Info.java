package com.esethucreates.RickAndMortySite.DTO;

public record Info(
	String next,
	int pages,
	String prev,
	int count
) {

//	TODO: Set the prev string to equal to zero if in the beginning
}
