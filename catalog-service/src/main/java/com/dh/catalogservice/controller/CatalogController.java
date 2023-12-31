package com.dh.catalogservice.controller;

import com.dh.catalogservice.client.IMovieClient;
import com.dh.catalogservice.client.ISerieClient;
import com.dh.catalogservice.model.Genre;
import com.dh.catalogservice.model.Movie;
import com.dh.catalogservice.model.Serie;
import com.dh.catalogservice.queue.MovieSender;
import com.dh.catalogservice.queue.SerieSender;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class CatalogController {

    @Autowired
    private IMovieClient iMovieClient;
    @Autowired
    private ISerieClient iSerieClient;

    //Se declaran los senders de MQ
    private final MovieSender sender;
    private final SerieSender senderS;

    //Constructor
    public CatalogController(IMovieClient iMovieClient, ISerieClient iSerieClient, MovieSender sender, SerieSender senderS) {
        this.iMovieClient = iMovieClient;
        this.iSerieClient = iSerieClient;
        this.sender = sender;
        this.senderS = senderS;
    }

    //Peticiones HTTP para MOVIES

    //Se configura circuit breaker, retry y método fallback para en caso de que falle el servicio
    @CircuitBreaker(name = "movies", fallbackMethod = "moviesFindAllEmpty")
    @Retry(name = "movies")
    @GetMapping("/catalog/movie/{genre}")
    public ResponseEntity<List<Movie>> getMovieByGenre (@PathVariable String genre){
        log.info("Calling movie service...");
        return iMovieClient.getMovieByGenre(genre,false); // false para que se ejecute normal, true para generar que falle
    }

    //Método Fallback - Circuit Breaker
    private ResponseEntity<List<Movie>> moviesFindAllEmpty(String genre, CallNotPermittedException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
    }

    @GetMapping("/catalog/random")
    public ResponseEntity<String> find() {
        return ResponseEntity.ok(iMovieClient.find());
    }
    @PostMapping("/saveMovie")
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        sender.send(movie);
        return iMovieClient.saveMovie(movie);
    }

    //Peticiones HTTP para SERIES
    @GetMapping
    public List<Serie> getAll() {
        return iSerieClient.getAll();
    }

    @GetMapping("/catalog/series/{genre}")
    public List<Serie> getCatalogSByGenre(@PathVariable String genre) {
        return iSerieClient.getSerieByGenre(genre);
    }

    @PostMapping("/saveSerie")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody Serie serie) {
        senderS.send(serie);
        iSerieClient.create(serie);
        return serie.getId();
    }

    //Peticion HTTP para consultar por genero tanto SERIES como MOVIES

    @GetMapping("/catalog/{genre}")
    public ResponseEntity<Genre> getCatalogByGenre(@PathVariable String genre) {
        List<Movie> movies = iMovieClient.getMovieByGenre(genre, false).getBody(); //false para que se ejecute normal, true para generar que falle
        List<Serie> series = iSerieClient.getSerieByGenre(genre);

        Genre catalogResponse = new Genre();
        catalogResponse.setMovie(movies);
        catalogResponse.setSerie(series);

        return ResponseEntity.ok(catalogResponse);
    }

}
