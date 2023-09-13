package com.dh.catalogservice.model;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Genre {
    private List<Movie> movie;
    private List<Serie> serie;

    public Genre() {

    }
}
