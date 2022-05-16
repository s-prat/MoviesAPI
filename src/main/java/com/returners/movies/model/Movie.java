package com.returners.movies.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false, name = "movie_id")
    private int id;

    @Column
    private String title;

    @Column
    private int year;

    @Column
    private int rating;

    @ManyToOne
    @JoinColumn(name="genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "certification_id")
    private Certification certification;

    @Column
    private String[] actors;


}
