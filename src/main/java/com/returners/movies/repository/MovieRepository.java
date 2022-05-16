package com.returners.movies.repository;

import com.returners.movies.model.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface MovieRepository extends CrudRepository<Movie, Integer> {
}
