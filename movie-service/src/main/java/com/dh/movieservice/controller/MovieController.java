package com.dh.movieservice.controller;

import com.dh.movieservice.model.Movie;
import com.dh.movieservice.queue.MovieListener;
import com.dh.movieservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author vaninagodoy
 */

@RestController
@RefreshScope
@RequestMapping("/movies")
public class MovieController {

    @Value("${idRandom}")
    private String idRandom;

    private final MovieService movieService;

    private final MovieListener listener;



    public MovieController(MovieService movieService, MovieListener listener) {
        this.movieService = movieService;
        this.listener = listener;
    }

    //Se asigna la variable booleana para el circuit breaker
    @GetMapping("/{genre}")
    ResponseEntity<List<Movie>> getMovieByGenre(@PathVariable String genre, @RequestParam(defaultValue = "true") Boolean throwError) {
        return ResponseEntity.ok().body(movieService.findByGenre(genre, throwError));
    }

    //Validate load balancer (port random)
    @GetMapping("/random")
    public ResponseEntity<String> find() {
        return ResponseEntity.ok(idRandom);
    }

    @PostMapping("/save")
    ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        listener.receive(movie);
        return ResponseEntity.ok().body(movieService.save(movie));
    }
}
