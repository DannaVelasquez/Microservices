package com.dh.catalogservice.client;

import com.dh.catalogservice.loadBalancerConfiguration.ConfigurationLoadBalancer;
import com.dh.catalogservice.model.Movie;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "movie-service")
@LoadBalancerClient(name = "movie-service", configuration = ConfigurationLoadBalancer.class)
public interface IMovieClient {

    @GetMapping("/movies/{genre}")
    ResponseEntity<List<Movie>> getMovieByGenre(@PathVariable String genre);

    @GetMapping("/movies/random")
    public String find();

    @PostMapping("movies/save")
    ResponseEntity<Movie> saveMovie(@RequestBody Movie movie);
}
