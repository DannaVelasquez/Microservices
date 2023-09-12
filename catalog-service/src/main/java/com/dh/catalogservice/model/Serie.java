package com.dh.catalogservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;



public record Serie(String id, String name, String genre, List<Season> seasons) {

    public String getId() {
        return getId();
    }

    public record Season(Integer seasonNumber, List<Chapter> chapters) {

        public record Chapter(String name, Integer number, String urlStream) {
        }
    }
}
