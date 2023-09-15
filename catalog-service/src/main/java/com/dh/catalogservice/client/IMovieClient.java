package com.dh.catalogservice.client;

import com.dh.catalogservice.loadBalancerConfiguration.ConfigurationLoadBalancer;
import com.dh.catalogservice.model.Movie;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "movie-service")
@LoadBalancerClient(name = "movie-service", configuration = ConfigurationLoadBalancer.class)
public interface IMovieClient {

    //Se agrega la variable booleana para circuit breaker
    @GetMapping("/movies/{genre}")
    ResponseEntity<List<Movie>> getMovieByGenre(@PathVariable String genre, @RequestParam Boolean throwError);

    @GetMapping("/movies/random")
    public String find();

    @PostMapping("movies/save")
    ResponseEntity<Movie> saveMovie(@RequestBody Movie movie);
}
