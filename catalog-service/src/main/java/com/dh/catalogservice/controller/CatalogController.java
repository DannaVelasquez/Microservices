package com.dh.catalogservice.controller;

import com.dh.catalogservice.client.IMovieClient;
import com.dh.catalogservice.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class CatalogController {

    @Value("${server.port}")
    private static String serverPort;
    @Autowired
    private IMovieClient iMovieClient;

    @GetMapping("/api/v1/movies/catalog/{genre}")
    public ResponseEntity<List<Movie>> getCatalogByGenre (@PathVariable String genre, HttpServletResponse response){
        response.addHeader("port", String.valueOf(serverPort));
        return iMovieClient.getMovieByGenre(genre);
    }

}
