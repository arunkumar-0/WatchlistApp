package com.arunKumar.watchlistApp.services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arunKumar.watchlistApp.entity.Movie;
import com.arunKumar.watchlistApp.repository.MovieRepo;

@Service
public class DatabaseService {
	@Autowired
	MovieRepo movieRepo;

	@Autowired
	RatingService ratingService;

	public void create(Movie movie) {
		String apiRating = ratingService.getRating(movie.getTitle());

		if (apiRating != null && !apiRating.isEmpty()) {
			// Use the rating from the API
			movie.setRating(Float.parseFloat(apiRating));
		}
//		} else if (movie.getRating() != 0.00) {
//			// Use the user's entered rating if API rating is not available
//			System.out.println("Using user-provided rating: " + movie.getRating());
//		} else {
//			// Default to a rating of 0.0 if neither API nor user rating is available
//			movie.setRating(0.0f);
//			System.out.println("No rating provided; defaulting to 0.0");
//		}

		movieRepo.save(movie);
	}


	public List<Movie> getAllMovies() {
		List<Movie> movies = movieRepo.findAll();

		// Sort movies by priority: "H" > "M" > "L"
		return movies.stream().sorted(Comparator.comparingInt(movie -> getPriorityValue(movie.getPriority())))
				.collect(Collectors.toList());
	}

	// Helper method to map priority to a sorting value
	private int getPriorityValue(String priority) {
		switch (priority) {
		case "H":
			return 1; // High priority comes first
		case "M":
			return 2; // Medium priority comes next
		case "L":
			return 3; // Low priority comes last
		default:
			return 4; // Handle missing or unknown priorities
		}
	}

	public Movie getMovieById(Integer id) {
		return movieRepo.findById(id).get();
	}

	public void update(Movie movie, Integer id) {

		Movie toBeUpdated = getMovieById(id);
		toBeUpdated.setTitle(movie.getTitle());
		toBeUpdated.setRating(movie.getRating());
		toBeUpdated.setComment(movie.getComment());
		toBeUpdated.setPriority(movie.getPriority());

		movieRepo.save(toBeUpdated);
	}

	public void delete(Integer id) {
		movieRepo.deleteById(id); // Delete the movie by its ID
	}
}
