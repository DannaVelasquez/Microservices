package com.dh.catalogservice.controller;

import com.dh.catalogservice.client.IMovieClient;
import com.dh.catalogservice.model.Movie;
import com.dh.catalogservice.queue.MovieSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CatalogController {

    @Autowired
    private IMovieClient iMovieClient;

    private final MovieSender sender;

    public CatalogController(IMovieClient iMovieClient, MovieSender sender) {
        this.iMovieClient = iMovieClient;
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

}
