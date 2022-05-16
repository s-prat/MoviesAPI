package com.returners.movies.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Genre {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false, name = "genre_id")
    private int genreId;

    @Column
    private String name;
}
