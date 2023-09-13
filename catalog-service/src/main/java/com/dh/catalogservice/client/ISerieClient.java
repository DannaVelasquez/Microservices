package com.dh.catalogservice.client;

import com.dh.catalogservice.loadBalancerConfiguration.ConfigurationLoadBalancer;
import com.dh.catalogservice.model.Movie;
import com.dh.catalogservice.model.Serie;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "serie-service")
public interface ISerieClient {

    @GetMapping("/series")
    public List<Serie> getAll();

    @GetMapping("/series/{genre}")
    public List<Serie> getSerieByGenre(@PathVariable String genre);

    @PostMapping("/series")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody Serie serie);
}
