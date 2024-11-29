package com.arunKumar.watchlistApp.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class RatingService {

	String apiUrl = "https://www.omdbapi.com/?apikey=5998ee5d&t=";

	public String getRating(String title) {
		try {
			RestTemplate template = new RestTemplate();
			ResponseEntity<ObjectNode> responseEntity = template.getForEntity(apiUrl + title, ObjectNode.class);
			ObjectNode jsonObject = responseEntity.getBody();

			if (jsonObject != null && jsonObject.has("imdbRating")) {
				return jsonObject.path("imdbRating").asText();
			} else {
				// System.out.println("No 'imdbRating' field found in API response for movie: "
				// + title);
				return null;
			}
		} catch (Exception e) {
			// System.out.println("Error fetching rating from API: " + e.getMessage());
			return null;
		}
	}

}
