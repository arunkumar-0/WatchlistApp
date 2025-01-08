package com.arunKumar.watchlistApp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.arunKumar.watchlistApp.entity.Movie;
import com.arunKumar.watchlistApp.services.DatabaseService;
import com.arunKumar.watchlistApp.services.MovieService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@Controller
public class MovieController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	MovieService movieService;

	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("index");

		// Fetch popular movies from the TMDB API
		String popularMoviesJson = movieService.getPopularMovies();

		// Use Jackson to parse the JSON response into a list of movie titles or other
		// data
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(popularMoviesJson);
			if (jsonNode.has("results")) {
				modelAndView.addObject("popularMovies", jsonNode.get("results"));
			} else {
				System.err.println("No 'results' key found in response");
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("errorMessage", "Failed to load popular movies.");
		}


		return modelAndView;
	}




	@GetMapping("/watchlistItemForm")
	public ModelAndView showWatchListForm(@RequestParam(required = false) Integer id) {
		String viewName = "watchlistItemForm";
		Map<String, Object> model = new HashMap<>();

		if (id == null) {
			model.put("watchlistItem", new Movie());
		} else {
			model.put("watchlistItem", databaseService.getMovieById(id));
		}

		return new ModelAndView(viewName, model);
	}

	@PostMapping("/watchlistItemForm")
	public ModelAndView submitWatchListForm(@Valid @ModelAttribute("watchlistItem") Movie movie,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("watchlistItemForm");
		}

		Integer id = movie.getId();
		if (id == null) {
			databaseService.create(movie);
		} else {
			databaseService.update(movie, id);
		}

		RedirectView rd = new RedirectView();
		rd.setUrl("/watchlist");

		return new ModelAndView(rd);
	}

	@GetMapping("/deleteItem")
	public RedirectView deleteItem(@RequestParam("id") Integer id) {
		databaseService.delete(id);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/watchlist");
		return redirectView;
	}

	@GetMapping("/watchlist")
	public ModelAndView getWatchlist() {
		String viewName = "watchlist";
		Map<String, Object> model = new HashMap<>();
		List<Movie> movieList = databaseService.getAllMovies();
		model.put("watchlistrows", movieList);
		model.put("noofmovies", movieList.size());
		return new ModelAndView(viewName, model);
	}
}
