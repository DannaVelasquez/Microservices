package com.dh.movieservice.service;

import com.dh.movieservice.model.Movie;
import com.dh.movieservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author vaninagodoy
 */

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    //Se implementa la variable booleana para generar la excepción
    public List<Movie> findByGenre(String genre, Boolean throwError) {
        if(throwError)
            throw new RuntimeException();

        return movieRepository.findByGenre(genre);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

}
