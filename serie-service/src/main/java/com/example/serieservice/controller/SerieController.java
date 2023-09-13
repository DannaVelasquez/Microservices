package com.example.serieservice.controller;

import com.example.serieservice.model.Serie;
import com.example.serieservice.queue.SerieListener;
import com.example.serieservice.service.SerieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author vaninagodoy
 */

@RestController
@RequestMapping("/series")
public class SerieController {

    private final SerieService serieService;

    private final SerieListener listener;

    public SerieController(SerieService serieService, SerieListener listener) {
        this.serieService = serieService;
        this.listener = listener;
    }

    @GetMapping
    public List<Serie> getAll() {
        return serieService.getAll();
    }

    @GetMapping("/{genre}")
    public List<Serie> getSerieByGenre(@PathVariable String genre) {
        return serieService.getSeriesBygGenre(genre);
    }

    @PostMapping
    ResponseEntity<String> create(@RequestBody Serie serie) {
        listener.receive(serie);
        return ResponseEntity.ok().body(serieService.create(serie));
    }
}
