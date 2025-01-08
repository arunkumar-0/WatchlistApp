package com.arunKumar.watchlistApp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arunKumar.watchlistApp.entity.Movie;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Integer> {

}
