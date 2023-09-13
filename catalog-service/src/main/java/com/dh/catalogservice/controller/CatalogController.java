package com.dh.catalogservice.controller;

import com.dh.catalogservice.client.IMovieClient;
import com.dh.catalogservice.client.ISerieClient;
import com.dh.catalogservice.model.Movie;
import com.dh.catalogservice.model.Serie;
import com.dh.catalogservice.queue.MovieSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CatalogController {

    @Autowired
    private IMovieClient iMovieClient;

    @Autowired
    private ISerieClient iSerieClient;

    private final MovieSender sender;

    public CatalogController(IMovieClient iMovieClient, ISerieClient iSerieClient, MovieSender sender) {
        this.iMovieClient = iMovieClient;
        this.iSerieClient = iSerieClient;
        this.sender = sender;
    }


    @GetMapping("/catalog/{genre}")
    public ResponseEntity<List<Movie>> getCatalogByGenre (@PathVariable String genre){
        return iMovieClient.getMovieByGenre(genre);
    }

    @GetMapping("/catalog/random")
    public ResponseEntity<String> find() {
        return ResponseEntity.ok(iMovieClient.find());
    }
    @PostMapping("/save")
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        sender.send(movie);
        return iMovieClient.saveMovie(movie);
    }

    @GetMapping
    public List<Serie> getAll() {
        return iSerieClient.getAll();
    }

    @GetMapping("/catalog/series/{genre}")
    public List<Serie> getCatalogSByGenre(@PathVariable String genre) {
        return iSerieClient.getSerieByGenre(genre);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody Serie serie) {
        iSerieClient.create(serie);
        return serie.getId();
    }
}
