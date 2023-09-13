package com.dh.catalogservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Genre {
    private List<Movie> movie;
    private List<Serie> serie;
}
