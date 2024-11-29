package com.arunKumar.watchlistApp.services;

import java.util.List;

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

		return movieRepo.findAll();
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
	}
