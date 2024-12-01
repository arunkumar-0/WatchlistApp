package com.arunKumar.watchlistApp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MovieService {

	@Value("${tmdb.api.key}")
	private String apiKey;

	private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3";

	public String getPopularMovies() {
		String url = UriComponentsBuilder.fromHttpUrl(TMDB_BASE_URL + "/movie/popular").queryParam("api_key", apiKey)
				.queryParam("language", "en-US").toUriString();

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, String.class); // Returns JSON response
	}
}

